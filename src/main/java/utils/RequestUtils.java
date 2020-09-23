package utils;

public class RequestUtils {
    public static final String REGEX = " ";
    public static final int URL_INDEX = 1;
    public static final int METHOD_TYPE_INDEX = 0;

    public static String extractMethod(String header) {
        return header.split(REGEX)[METHOD_TYPE_INDEX];
    }

    public static String extractPath(String header) {
        String[] method = header.split(REGEX);
        return method[URL_INDEX];
    }

    public static String extractTitleOfModel(String header) {
        String[] method = header.split(REGEX);
        return method[URL_INDEX].split("/")[1].toUpperCase();
    }

    public static String extractExtension(String url) {
        String[] names = url.split("\\.");
        return names[names.length - 1];
    }
}
