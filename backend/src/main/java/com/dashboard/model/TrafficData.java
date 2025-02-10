package main.java.com.dashboard.model;

public class TrafficData {
    private String ipAddress;
    private int hourlyTraffic;

    // Constructors
    public TrafficData() {}

    public TrafficData(String ipAddress, int hourlyTraffic) {
        this.ipAddress = ipAddress;
        this.hourlyTraffic = hourlyTraffic;
    }

    // Getters and Setters
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getHourlyTraffic() {
        return hourlyTraffic;
    }

    public void setHourlyTraffic(int hourlyTraffic) {
        this.hourlyTraffic = hourlyTraffic;
    }
}
