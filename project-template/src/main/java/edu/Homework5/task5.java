package edu.Homework5;
import java.util.regex.Pattern;

public class task5 {

    public static void main(String[] args) {
        String licensePlate = "А123ВЕ777";
        boolean isValid = validateLicensePlate(licensePlate);
        System.out.println(isValid);
    }

    public static boolean validateLicensePlate(String licensePlate) {
        String pattern = "[АВЕКМНОРСТУХABEKMHOPCTYX]{1}\\d{3}[АВЕКМНОРСТУХABEKMHOPCTYX]{2}\\d{2,3}";
        return Pattern.matches(pattern, licensePlate);
    }
}
