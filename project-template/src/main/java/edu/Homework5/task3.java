package edu.Homework5;
import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
    import java.util.Optional;

public class task3 {

    public static void main(String[] args) {
        String dateString = "1/3/20";
        Optional<LocalDate> result = parseDate(dateString);
        result.ifPresent(System.out::println);
    }

    public static Optional<LocalDate> parseDate(String string) {
        DateTimeFormatter[] formatters = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy-MM-d"),
            DateTimeFormatter.ofPattern("M/d/yyyy"),
            DateTimeFormatter.ofPattern("M/d/yy"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("MMMM d, yyyy"),
            DateTimeFormatter.ofPattern("MMMM d, yy"),
            DateTimeFormatter.ofPattern("EEEE"),
            DateTimeFormatter.ofPattern("EEEEEE"),
            DateTimeFormatter.ofPattern("today"),
            DateTimeFormatter.ofPattern("tomorrow"),
            DateTimeFormatter.ofPattern("yesterday"),
            DateTimeFormatter.ofPattern("d 'day' ago"),
            DateTimeFormatter.ofPattern("d 'days' ago")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDate date = LocalDate.parse(string, formatter);
                return Optional.of(date);
            } catch (Exception ignored) {
            }
        }

        return Optional.empty();
    }
}
