package edu.Homework5;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class task6 {

    public static void main(String[] args) {
        String T = "achfdbaabgabcaabg";
        String S = "abc";
        boolean isSubsequence = isSubsequence(S, T);
        System.out.println(isSubsequence);
    }

    public static boolean isSubsequence(String S, String T) {
        String regex = ".*" + S + ".*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(T);
        return matcher.matches();
    }
}
