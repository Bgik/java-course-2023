package edu.progect_dz_5;
import java.io.IOException;
    import java.net.URI;
    import java.net.URISyntaxException;
    import java.net.http.HttpClient;
    import java.net.http.HttpRequest;
    import java.net.http.HttpResponse;
    import java.nio.charset.StandardCharsets;
    import java.nio.file.FileVisitOption;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.time.LocalDateTime;
    import java.time.OffsetDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.*;
    import java.util.stream.Collectors;
    import java.util.stream.Stream;

public class task {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -jar nginx-log-stats.jar --path <log-file-path-or-url> [--from <from-date> --to <to-date>] [--format <markdown-or-adoc>]");
            System.exit(1);
        }

        String logFilePathOrUrl = null;
        String fromDate = null;
        String toDate = null;
        String outputFormat = "markdown";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--path":
                    logFilePathOrUrl = args[++i];
                    break;
                case "--from":
                    fromDate = args[++i];
                    break;
                case "--to":
                    toDate = args[++i];
                    break;
                case "--format":
                    outputFormat = args[++i].toLowerCase();
                    break;
            }
        }

        try {
            Stream<LogRecord> logRecords = readLogRecords(logFilePathOrUrl);
            LogReport logReport = generateLogReport(logRecords, fromDate, toDate);
            printLogReport(logReport, outputFormat);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Stream<LogRecord> readLogRecords(String logFilePathOrUrl) throws IOException, URISyntaxException, InterruptedException {
        if (logFilePathOrUrl.startsWith("http")) {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(logFilePathOrUrl)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return Arrays.stream(response.body().split("\n")).map(LogRecord::parse);
        } else {
            List<Path> logFiles = Files.find(Path.of(logFilePathOrUrl), Integer.MAX_VALUE,
                    (path, attributes) -> path.toString().endsWith(".log") && attributes.isRegularFile(),
                    FileVisitOption.FOLLOW_LINKS)
                .collect(Collectors.toList());

            return logFiles.parallelStream()
                .flatMap(file -> {
                    try {
                        return Files.lines(file, StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return Stream.empty();
                    }
                })
                .map(LogRecord::parse);
        }
    }

    private static LogReport generateLogReport(Stream<LogRecord> logRecords, String fromDate, String toDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime fromDateTime = (fromDate != null) ? LocalDateTime.parse(fromDate, formatter) : null;
        LocalDateTime toDateTime = (toDate != null) ? LocalDateTime.parse(toDate, formatter) : null;

        Stream<LogRecord> filteredLogRecords = logRecords.filter(logRecord ->
                ((fromDateTime == null) || logRecord.getDateTime().isAfter(OffsetDateTime.from(fromDateTime))) &&
                        ((toDateTime == null) || logRecord.getDateTime().isBefore(OffsetDateTime.from(toDateTime))));

        List<String> fileNames = filteredLogRecords.map(LogRecord::getFileName).distinct().collect(Collectors.toList());

        long totalRequests = filteredLogRecords.count();
        double averageResponseSize = filteredLogRecords.mapToInt(LogRecord::getResponseSize).average().orElse(0.0);

        Map<String, Long> resourceCounts = filteredLogRecords.collect(Collectors.groupingBy(LogRecord::getResource, Collectors.counting()));
        Map<Integer, Long> statusCodeCounts = filteredLogRecords.collect(Collectors.groupingBy(LogRecord::getStatusCode, Collectors.counting()));

        return new LogReport(fileNames, fromDateTime, toDateTime, totalRequests, averageResponseSize, resourceCounts, statusCodeCounts);
    }

    private static void printLogReport(LogReport logReport, String outputFormat) {
        switch (outputFormat) {
            case "markdown":
            //    System.out.println(logReport.toMarkdown());
                break;
            case "adoc":
              //  System.out.println(logReport.toAdoc());
                break;
            default:
                System.out.println("Unsupported output format: " + outputFormat);
        }
    }
}

class LogRecord {
    private OffsetDateTime dateTime;
    private String fileName;
    private String resource;
    private int statusCode;
    private int responseSize;

    public LogRecord(OffsetDateTime dateTime, String fileName, String resource, int statusCode, int responseSize) {
        this.dateTime = dateTime;
        this.fileName = fileName;
        this.resource = resource;
        this.statusCode = statusCode;
        this.responseSize = responseSize;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public String getFileName() {
        return fileName;
    }

    public String getResource() {
        return resource;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    static LogRecord parse(String logLine) {
        // логи
        String[] parts = logLine.split("\\s+");
        OffsetDateTime dateTime = OffsetDateTime.parse(parts[0] + "T" + parts[1]);
        String fileName = parts[2];
        String resource = parts[3];
        int statusCode = Integer.parseInt(parts[4]);
        int responseSize = Integer.parseInt(parts[5]);
        return new LogRecord(dateTime, fileName, resource, statusCode, responseSize);
    }
}

class LogReport {
    private List<String> fileNames;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private long totalRequests;
    private double averageResponseSize;
    private Map<String, Long> resourceCounts;
    private Map<Integer, Long> statusCodeCounts;

    public LogReport(
        List<String> fileNames,
        LocalDateTime fromDate,
        LocalDateTime toDate,
        long totalRequests,
        double averageResponseSize,
        Map<String, Long> resourceCounts,
        Map<Integer, Long> statusCodeCounts
    ) {
        this.fileNames = fileNames;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.totalRequests = totalRequests;
        this.averageResponseSize = averageResponseSize;
        this.resourceCounts = resourceCounts;
        this.statusCodeCounts = statusCodeCounts;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public double getAverageResponseSize() {
        return averageResponseSize;
    }

    public Map<String, Long> getResourceCounts() {
        return resourceCounts;
    }

    public Map<Integer, Long> getStatusCodeCounts() {
        return statusCodeCounts;
    }

}
