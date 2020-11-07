package utils;

public class StringUtils {
    public static String getFilename(String request) {
        return request
            .split(System.lineSeparator())[0]
            .split(" ")[1];
    }
}
