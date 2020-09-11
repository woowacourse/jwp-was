package utils;

public class PathUtils {
    public static final String DELIMITER = " ";
    public static final int PATH_INDEX = 1;
    public static final String BASE_URL = "./templates";

    public static String extractPath(String requestLine) {
        String[] info = requestLine.split(DELIMITER);
        return BASE_URL + info[PATH_INDEX];
    }
}
