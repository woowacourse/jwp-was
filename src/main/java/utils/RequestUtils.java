package utils;

public class RequestUtils {
    public static final String REGEX = " ";
    public static final int URL_INDEX = 1;
    public static final int REQUEST_TYPE_INDEX = 0;

    public static boolean isPost(String header) {
        String[] method = header.split(REGEX);
        return "POST".equals(method[REQUEST_TYPE_INDEX]);
    }

    public static String extractUrl(String header) {
        String[] method = header.split(REGEX);
        return method[URL_INDEX];
    }

    public static String extractTitleOfModel(String header) {
        String[] method = header.split(REGEX);
        return method[URL_INDEX].split("/")[1];
    }
}
