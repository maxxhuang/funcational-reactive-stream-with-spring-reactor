package tenam.learning.reactivespringreactor.devicesimulator;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.NamingConvention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SimulatorApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(SimulatorApplication.class)
                .web(WebApplicationType.NONE)
                .build()
                .run(args);
    }


    @Value("${device.controller.host:localhost}")
    private String controllerHost;

    @Value("${device.controller.port:8080}")
    private int controllerPort;

    @Value("${device.simulator.count:1000}")
    private int deviceCount;

    @Value("${device.simulator.heartbeat.interval:10}")
    private int heartbeatIntervalInSeconds;

    @Value("${device.simulator.stats.interval:20}")
    private int statsIntervalInSeconds;

    @Autowired
    private MeterRegistry meterRegistry;


    @PostConstruct
    public void init() {
        this.meterRegistry.config().namingConvention(NamingConvention.dot);
    }

    @Bean
    public UrlBuilder urlBuilder() {
        return new UrlBuilder(this.controllerHost, this.controllerPort);
    }

    @Bean
    public DeviceSimulator deviceSimulator() {
        return new DeviceSimulator(
                this.deviceCount,
                this.heartbeatIntervalInSeconds,
                this.statsIntervalInSeconds,
                urlBuilder(),
                this.meterRegistry,
                webClient()
        );
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
