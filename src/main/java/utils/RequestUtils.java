package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class RequestUtils {
    public static final String SPACE_REGEX = " ";
    public static final int URL_INDEX = 1;
    public static final int METHOD_TYPE_INDEX = 0;
    public static final String SLASH_REGEX = "/";
    public static final int MODEL_INDEX = 1;
    public static final String AND_REGEX = "&";
    public static final String EQUALS_REGEX = "=";

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

    public static Map<String, String> extractParameter(String body) {
        String[] parameters = body.split(AND_REGEX);
        return Arrays.stream(parameters)
            .map(parameter -> parameter.split(EQUALS_REGEX))
            .collect(Collectors.toMap(strings -> strings[0], strings -> strings[1], (k, v) -> v, TreeMap::new));
    }
}
