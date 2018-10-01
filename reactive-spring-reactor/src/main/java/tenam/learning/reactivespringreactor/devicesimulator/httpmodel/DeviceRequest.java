package tenam.learning.reactivespringreactor.devicesimulator.httpmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DeviceRequest {

    public enum ReportType {heartbeat, stats}


    private String mac;
    private long timestamp;
    private ReportType reportType;

    @JsonIgnore
    private String requestUrl;


    public DeviceRequest() {
    }

    public DeviceRequest(String mac, long timestamp, ReportType reportType, String requestUrl) {
        this.mac = mac;
        this.timestamp = timestamp;
        this.reportType = reportType;
        this.requestUrl = requestUrl;
    }


    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
