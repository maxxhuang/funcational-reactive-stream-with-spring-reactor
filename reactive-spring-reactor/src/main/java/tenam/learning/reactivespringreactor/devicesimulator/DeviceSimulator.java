package tenam.learning.reactivespringreactor.devicesimulator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tenam.learning.reactivespringreactor.devicesimulator.generator.MacGenerator;
import tenam.learning.reactivespringreactor.devicesimulator.generator.ValueGenerator;
import tenam.learning.reactivespringreactor.devicesimulator.httpmodel.DeviceRequest;
import tenam.learning.reactivespringreactor.devicesimulator.httpmodel.DeviceRequest.ReportType;
import tenam.learning.reactivespringreactor.devicesimulator.httpmodel.DeviceResponse;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class DeviceSimulator implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DeviceSimulator.class);


    private int deviceCount;

    private int heartbeatIntervalInSeconds;

    private int statsIntervalInSeconds;

    private UrlBuilder urlBuilder;

    private MeterRegistry meterRegistry;

    private WebClient webClient;

    private ValueGenerator<String> macGenerator = new MacGenerator("AA", "BB");

    private Counter heartbeatCounter;
    private Counter statsCounter;

    private Counter errorHeartbeatCounter;
    private Counter errorStatsCounter;


    public DeviceSimulator(
            int deviceCount,
            int heartbeatIntervalInSeconds,
            int statsIntervalInSeconds,
            UrlBuilder urlBuilder,
            MeterRegistry meterRegistry,
            WebClient webClient) {

        this.deviceCount = deviceCount;
        this.heartbeatIntervalInSeconds = heartbeatIntervalInSeconds;
        this.statsIntervalInSeconds = statsIntervalInSeconds;
        this.urlBuilder = urlBuilder;
        this.webClient = webClient;

        initMeters(meterRegistry);
    }

    private void initMeters(MeterRegistry meterRegistry) {
        this.heartbeatCounter = meterRegistry.counter("device.simulator.counter.heartbeat");
        this.statsCounter = meterRegistry.counter("device.simulator.counter.stats");

        this.errorHeartbeatCounter = meterRegistry.counter("device.simulator.counter.errorHeartbeat");
        this.errorStatsCounter = meterRegistry.counter("device.simulator.counter.errorStats");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("Start simulating {} devices reporting heartbeats every {} seconds and stats every {} seconds",
                this.deviceCount, this.heartbeatIntervalInSeconds, this.statsIntervalInSeconds);

        List<String> deviceMacs = this.macGenerator.generate(this.deviceCount);

        Flux.fromIterable(deviceMacs)
                .flatMap(mac -> createDeviceRequestStream(mac),
                        this.deviceCount, 2)
                .flatMap(this::sendRequest)
                .subscribe(this::logAndUpdateMetrics);

        while(true) {
            TimeUnit.SECONDS.sleep(5);
        }
    }

    private void logAndUpdateMetrics(DeviceResponse response) {

        response.handle(
                () -> {
                    logger.info("successfully sent {} request for {}",
                            response.getDeviceRequest().getReportType(),
                            response.getDeviceRequest().getMac());

                    switch (response.getDeviceRequest().getReportType()) {
                        case heartbeat: this.heartbeatCounter.increment(); break;
                        case stats: this.statsCounter.increment(); break;
                    }
                },

                error -> {
                    logger.error("failed to send {} request for {}: {}",
                            response.getDeviceRequest().getReportType(),
                            response.getDeviceRequest().getMac(),
                            error.getMessage());

                    switch (response.getDeviceRequest().getReportType()) {
                        case heartbeat: this.errorHeartbeatCounter.increment(); break;
                        case stats: this.errorStatsCounter.increment(); break;
                    }
                }
        );

    }

    private Mono<DeviceResponse> sendRequest(DeviceRequest request) {
        return this.webClient.post().uri(request.getRequestUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(request)
                .retrieve()
                .bodyToMono(Void.class)
//                .doOnError(e -> e.printStackTrace())
                .then(Mono.defer(() -> Mono.just(DeviceResponse.createSuccessful(request))))
                .onErrorResume(e -> Mono.just(DeviceResponse.createFailed(request, e)));
    }

    private Flux<DeviceRequest> createDeviceRequestStream(String deviceMac) {
        logger.info("Starting streaming out reports for {}", deviceMac);

        return Flux.merge(
                createDeviceHeartbeatStream(deviceMac),
                createDeviceStatsStream(deviceMac)
        );
    }

    private Flux<DeviceRequest> createDeviceHeartbeatStream(String deviceMac) {
        return Flux.interval(Duration.ofSeconds(this.heartbeatIntervalInSeconds))
                .map(n -> new DeviceRequest(
                        deviceMac,
                        System.currentTimeMillis(),
                        ReportType.heartbeat,
                        this.urlBuilder.url(ReportType.heartbeat, deviceMac)));
    }

    private Flux<DeviceRequest> createDeviceStatsStream(String deviceMac) {
        return Flux.interval(Duration.ofSeconds(this.statsIntervalInSeconds))
                .map(n -> new DeviceRequest(
                        deviceMac,
                        System.currentTimeMillis(),
                        ReportType.stats,
                        this.urlBuilder.url(ReportType.stats, deviceMac)));
    }
}
