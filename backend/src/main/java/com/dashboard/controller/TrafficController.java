package main.java.com.dashboard.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TrafficController {

    @CrossOrigin(origins = "http://localhost:3000") // Add CORS for this endpoint
    @GetMapping("/api/traffic/ip")
    public List<String> getIPTraffic() {
        return List.of("192.168.1.1 - 50%", "10.0.0.1 - 35%");
    }

    @CrossOrigin(origins = "http://localhost:3000") // Add CORS for this endpoint
    @GetMapping("/api/traffic/hourly")
    public List<String> getHourlyTraffic() {
        return List.of("10:00 - 40%", "14:00 - 30%");
    }
}
