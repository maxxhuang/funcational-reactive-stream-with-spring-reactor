package tenam.learning.reactivespringreactor.devicesimulator.httpmodel;

import java.util.Optional;
import java.util.function.Consumer;

public class DeviceResponse {

    public static DeviceResponse createSuccessful(DeviceRequest deviceRequest) {
        return new DeviceResponse(deviceRequest,
                System.currentTimeMillis() - deviceRequest.getTimestamp(),
                Optional.empty());
    }

    public static DeviceResponse createFailed(DeviceRequest deviceRequest, Throwable error) {
        return new DeviceResponse(deviceRequest,
                System.currentTimeMillis() - deviceRequest.getTimestamp(),
                Optional.of(error));
    }


    private DeviceRequest deviceRequest;
    private long elapsedTime;
    private Optional<Throwable> error;


    public DeviceResponse(DeviceRequest deviceRequest, long elapsedTime, Optional<Throwable> error) {
        this.deviceRequest = deviceRequest;
        this.elapsedTime = elapsedTime;
        this.error = error;
    }


    public boolean isSuccessful() {
        return this.error.isPresent() == false;
    }

    public void handle(Runnable successhandler, Consumer<Throwable> errorHandler) {
        if (this.error.isPresent()) this.error.ifPresent(errorHandler);
        else successhandler.run();
    }

    public DeviceRequest getDeviceRequest() {
        return deviceRequest;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public Optional<Throwable> getError() {
        return error;
    }
}
