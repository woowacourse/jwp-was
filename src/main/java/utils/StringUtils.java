package utils;

public class StringUtils {

    private static final int VALID_REQUEST_SIZE = 3;

    public static String getRequestLocation(String line) {
        String[] requestLine = line.split(" ");
        if (requestLine.length != VALID_REQUEST_SIZE) {
            throw new IllegalArgumentException("Invalid Request");
        }
        return requestLine[1];
    }
}
