package tenam.learning.reactivespringreactor.devicecontroller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.NamingConvention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class DeviceControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceControllerApplication.class, args);
    }


    @Autowired
    private MeterRegistry meterRegistry;


    @PostConstruct
    public void init() {
        this.meterRegistry.config().namingConvention(NamingConvention.dot);
    }

}
