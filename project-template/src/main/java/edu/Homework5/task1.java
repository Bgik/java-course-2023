package edu.Homework5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class task1 {

    public static void main(String[] args) {
        List<String> sessions = List.of(
            "2022-03-12, 20:20 - 2022-03-12, 23:50",
            "2022-04-01, 21:30 - 2022-04-02, 01:20"
        );

        Duration averageDuration = calculateAverageSessionDuration(sessions);
        System.out.println(formatDuration(averageDuration));
    }

    public static Duration calculateAverageSessionDuration(List<String> sessions) {
        long totalSeconds = 0;

        for (String session : sessions) {
            String[] timestamps = session.split(" - ");
            LocalDateTime start = LocalDateTime.parse(timestamps[0], DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm"));
            LocalDateTime end = LocalDateTime.parse(timestamps[1], DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm"));
            totalSeconds += Duration.between(start, end).getSeconds();
        }

        long averageSeconds = totalSeconds / sessions.size();
        return Duration.ofSeconds(averageSeconds);
    }

    public static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = (duration.toMinutes() % 60);
        return hours + "ч " + minutes + "м";
    }
}
