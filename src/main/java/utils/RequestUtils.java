package utils;

public class RequestUtils {
    public static final String SPACE_REGEX = " ";
    public static final int URL_INDEX = 1;
    public static final int METHOD_TYPE_INDEX = 0;
    public static final String SLASH_REGEX = "/";
    public static final int MODEL_INDEX = 1;

    public static String extractMethod(String header) {
        return header.split(SPACE_REGEX)[METHOD_TYPE_INDEX];
    }

    public static String extractPath(String header) {
        String[] method = header.split(SPACE_REGEX);
        return method[URL_INDEX];
    }

    public static String extractTitleOfModel(String path) {
        return path.split(SLASH_REGEX)[MODEL_INDEX].toUpperCase();
    }

    public static String extractExtension(String url) {
        String[] names = url.split("\\.");
        return names[names.length - 1];
    }
}
