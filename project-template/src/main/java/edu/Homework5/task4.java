package edu.Homework5;
import java.util.regex.Pattern;

public class task4 {

    public static void main(String[] args) {
        String password = "Abc123!@";
        boolean isValid = validatePassword(password);
        System.out.println(isValid);
    }

    public static boolean validatePassword(String password) {
        String pattern = ".*[~!@#$%^&*|].*";
        return Pattern.matches(pattern, password);
    }
}
