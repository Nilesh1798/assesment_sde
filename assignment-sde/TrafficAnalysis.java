import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TrafficAnalysis {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String logFilePath = "server_log.txt";
        System.out.println("Enter the desired date in format DD/Mmm/YYYY:");
        String targetDate = scanner.nextLine();

        List<LogEntry> logs = parseLogFile(logFilePath);

        findTopContributors(logs, targetDate, 85, "IP");
        findTopContributors(logs, targetDate, 70, "Hour");
    }

    static class LogEntry {
        String ip;
        Date timestamp;
        int hour;

        LogEntry(String ip, Date timestamp, int hour) {
            this.ip = ip;
            this.timestamp = timestamp;
            this.hour = hour;
        }
    }

    private static List<LogEntry> parseLogFile(String logFilePath) throws IOException {
        List<LogEntry> logs = new ArrayList<>();
        SimpleDateFormat logDateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length > 3) {
                    try {
                        String ip = parts[0];
                        String timestampStr = parts[3].replace("[", "");
                        Date timestamp = logDateFormat.parse(timestampStr);

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(timestamp);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);

                        logs.add(new LogEntry(ip, timestamp, hour));
                    } catch (ParseException e) {
                        System.err.println("Error parsing date for line: " + line);
                    }
                }
            }
        }

        return logs;
    }

    private static void findTopContributors(List<LogEntry> logs, String targetDate, int thresholdPercentage, String mode) {
        Map<String, Integer> ipCount = new HashMap<>();
        Map<Integer, Integer> hourlyCount = new HashMap<>();
        SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);

        for (LogEntry log : logs) {
            String logDate = targetDateFormat.format(log.timestamp);
            if (logDate.equals(targetDate)) {
                if (mode.equals("IP")) {
                    ipCount.put(log.ip, ipCount.getOrDefault(log.ip, 0) + 1);
                } else if (mode.equals("Hour")) {
                    hourlyCount.put(log.hour, hourlyCount.getOrDefault(log.hour, 0) + 1);
                }
            }
        }

        if (mode.equals("IP")) {
            displayTopContributors(ipCount, thresholdPercentage, "IP Addresses");
        } else if (mode.equals("Hour")) {
            displayTopContributors(hourlyCount, thresholdPercentage, "Hours");
        }
    }

    private static <K> void displayTopContributors(Map<K, Integer> countMap, int thresholdPercentage, String label) {
        List<Map.Entry<K, Integer>> sortedEntries = new ArrayList<>(countMap.entrySet());
        sortedEntries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        int total = sortedEntries.stream().mapToInt(Map.Entry::getValue).sum();
        double threshold = (thresholdPercentage / 100.0) * total;

        System.out.println("\nTop " + label + " contributing to " + thresholdPercentage + "% of the traffic:");
        double cumulative = 0;
        for (Map.Entry<K, Integer> entry : sortedEntries) {
            cumulative += entry.getValue();
            System.out.printf("%-10s | %d%n", entry.getKey(), entry.getValue());
            if (cumulative >= threshold) break;
        }
    }
}
