package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StringUtils {
    public static final String SPACE = " ";
    public static final String QUESTION_MARK = "\\?";
    public static final String AMPERSAND = "&";
    public static final String EQUAL = "=";

    public static String getFilename(String request) {
        return getFirstLine(request)
            .split(SPACE)[1]
            .split(QUESTION_MARK)[0];
    }

    public static String getMethod(String request) {
        return getFirstLine(request)
            .split(SPACE)[0];
    }

    public static String getFirstLine(String request) {
        return request.split(System.lineSeparator())[0];
    }

    public static String trimExtensionIfExists(String path) {
        return path.split("//.")[0];
    }

    public static Map<String, String> getQueryParameters(String request) {
        String firstLine = getFirstLine(request);
        if (!firstLine.contains("?")) {
            return new HashMap<>();
        }
        return Arrays.stream(firstLine
            .split(SPACE)[1]
            .split(QUESTION_MARK)[1]
            .split(AMPERSAND))
            .collect(Collectors.toMap(
                it -> it.split(EQUAL)[0],
                it -> it.split(EQUAL)[1])
            );
    }
}
