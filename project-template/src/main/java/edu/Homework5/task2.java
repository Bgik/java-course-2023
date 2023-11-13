package edu.Homework5;
import java.time.DayOfWeek;
    import java.time.LocalDate;
    import java.time.temporal.TemporalAdjusters;
    import java.util.ArrayList;
    import java.util.List;

public class task2 {

    public static void main(String[] args) {
        int year = 2024;
        List<LocalDate> fridayThirteens = findFridayThirteens(year);
        System.out.println("Friday the 13th dates for " + year + ": " + fridayThirteens);

        LocalDate currentDate = LocalDate.now();
        LocalDate nextFridayThirteen = findNextFridayThirteen(currentDate);
        System.out.println("Next Friday the 13th after today: " + nextFridayThirteen);
    }

    public static List<LocalDate> findFridayThirteens(int year) {
        List<LocalDate> fridayThirteens = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            LocalDate date = LocalDate.of(year, month, 13);
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridayThirteens.add(date);
            }
        }

        return fridayThirteens;
    }

    public static LocalDate findNextFridayThirteen(LocalDate currentDate) {
        return currentDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).withDayOfMonth(13);
    }
}
