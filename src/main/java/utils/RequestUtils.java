package utils;

public class RequestUtils {
    public static final String REGEX = " ";
    public static final int FILE_NAME_INDEX = 1;

    public static String extractFileName(String header) {
        String[] method = header.split(REGEX);
        return method[FILE_NAME_INDEX];
    }
}
