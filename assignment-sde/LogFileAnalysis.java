import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogFileAnalysis {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String logFilePath = "server_log.txt";
        System.out.println("Enter the desired date in format DD/Mmm/YYYY:");
        String targetDate = scanner.nextLine();

        List<LogEntry> logs = parseLogFile(logFilePath);
        displayIPHistogram(logs, targetDate);
        displayHourlyHistogram(logs, targetDate);
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

    private static void displayIPHistogram(List<LogEntry> logs, String targetDate) {
        Map<String, Integer> ipCount = new HashMap<>();
        SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);

        for (LogEntry log : logs) {
            String logDate = targetDateFormat.format(log.timestamp);
            if (logDate.equals(targetDate)) {
                ipCount.put(log.ip, ipCount.getOrDefault(log.ip, 0) + 1);
            }
        }

        System.out.println("\nIP Address            Occurrences");
        System.out.println("-----------------------------------");
        ipCount.forEach((ip, count) -> System.out.printf("%-20s | %d%n", ip, count));
    }

    private static void displayHourlyHistogram(List<LogEntry> logs, String targetDate) {
        Map<Integer, Integer> hourlyCount = new HashMap<>();
        SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);

        for (LogEntry log : logs) {
            String logDate = targetDateFormat.format(log.timestamp);
            if (logDate.equals(targetDate)) {
                hourlyCount.put(log.hour, hourlyCount.getOrDefault(log.hour, 0) + 1);
            }
        }

        System.out.println("\nHour  | Visitors");
        System.out.println("--------------------");
        for (int hour = 0; hour < 24; hour++) {
            System.out.printf("%02d    | %d%n", hour, hourlyCount.getOrDefault(hour, 0));
        }
    }
}
