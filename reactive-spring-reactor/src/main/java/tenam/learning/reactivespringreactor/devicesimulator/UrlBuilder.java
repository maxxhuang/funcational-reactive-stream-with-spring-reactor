package tenam.learning.reactivespringreactor.devicesimulator;

import tenam.learning.reactivespringreactor.devicesimulator.httpmodel.DeviceRequest.ReportType;

public class UrlBuilder {

    private boolean isSecure = false;

    private String host;

    private int port;

    public UrlBuilder(boolean isSecure, String host, int port) {
        this.isSecure = isSecure;
        this.host = host;
        this.port = port;
    }

    public UrlBuilder(String host, int port) {
        this(false, host, port);
    }

    public String url(ReportType reportType, String deviceMac) {
        return new StringBuilder()
                .append(this.isSecure ? "https://" : "http://")
                .append(this.host)
                .append(":")
                .append(this.port)
                .append("/")
                .append(deviceMac)
                .append("/")
                .append(reportType.name())
                .toString();
    }

}
