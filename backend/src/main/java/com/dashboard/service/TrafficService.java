package main.java.com.dashboard.service;

import main.java.com.dashboard.model.TrafficData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrafficService {

    public List<TrafficData> getIPContributions() {
        // Simulate data for the IP contribution
        List<TrafficData> data = new ArrayList<>();
        data.add(new TrafficData("192.168.1.1", 50));
        data.add(new TrafficData("192.168.1.2", 30));
        data.add(new TrafficData("192.168.1.3", 20));
        return data;
    }

    public List<TrafficData> getHourlyContributions() {
        // Simulate data for the hourly contribution
        List<TrafficData> data = new ArrayList<>();
        data.add(new TrafficData("10 AM", 40));
        data.add(new TrafficData("11 AM", 30));
        data.add(new TrafficData("12 PM", 30));
        return data;
    }
}
