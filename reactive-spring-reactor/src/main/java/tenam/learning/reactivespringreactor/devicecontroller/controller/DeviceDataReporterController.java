package tenam.learning.reactivespringreactor.devicecontroller.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tenam.learning.reactivespringreactor.devicesimulator.httpmodel.DeviceRequest;

@RestController
public class DeviceDataReporterController {

    private static Logger logger = LoggerFactory.getLogger(DeviceDataReporterController.class);


    private Counter heartbeatCounter;
    private Counter statsCounter;


    public DeviceDataReporterController(MeterRegistry meterRegistry) {
        this.heartbeatCounter = meterRegistry.counter("device.controller.counter.heartbeat");
        this.statsCounter = meterRegistry.counter("device.controller.counter.stats");
    }

    @PostMapping("/{mac}/heartbeat")
    public void reportHeartbeat(@PathVariable("mac") String mac,
                                @RequestBody DeviceRequest deviceRequest) {
        log(mac, deviceRequest);
        this.heartbeatCounter.increment();
    }

    @PostMapping(value = "/{mac}/stats")
    public void reportStats(@PathVariable("mac") String mac,
                            @RequestBody DeviceRequest deviceRequest) {
        log(mac, deviceRequest);
        this.statsCounter.increment();
    }

    private static void log(String mac, DeviceRequest request) {
        logger.info("received {} request from {}",
                request.getReportType(), request.getMac());
    }
}
