package utils;

public class RequestPathUtil {
    private static final String SPACE = " ";

    public static String getPathFromRequestLine(String requestLine) {
        String[] requestLines = requestLine.split(SPACE);
        return requestLines[1];
    }
}
