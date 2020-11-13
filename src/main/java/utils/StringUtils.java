package utils;

public class StringUtils {

    public static String deleteLastEmptyLine(String token) {
        return token.split(System.lineSeparator())[0];
    }
}
