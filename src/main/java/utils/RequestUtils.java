package utils;

public class RequestUtils {
    private static final int FILE_PATH_INDEX = 1;
    private static final String DIRECTORY_PATH = "./templates";

    public static String extractFilePath(String requestLine) {
        String[] tokens = requestLine.split(" ");
        return DIRECTORY_PATH + tokens[FILE_PATH_INDEX];
    }
}
