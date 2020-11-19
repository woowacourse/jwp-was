package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import webserver.Method;

public class RequestUtils {
    public static final String SPACE = " ";
    public static final String QUESTION_MARK = "\\?";
    public static final String AMPERSAND = "&";
    public static final String EQUAL = "=";
    public static final String COLON = ": ";

    public static String getFilename(String request) {
        return getFirstLine(request)
            .split(SPACE)[1]
            .split(QUESTION_MARK)[0];
    }

    public static Method getMethod(String request) {
        return Method.valueOf(getFirstLine(request)
            .split(SPACE)[0]);
    }

    public static String getFirstLine(String request) {
        return request.split(System.lineSeparator())[0];
    }

    public static Map<String, String> getHeader(String request) {
        return Arrays.stream(request.split(System.lineSeparator()))
            .skip(1)
            .collect(Collectors.toMap(
                it -> it.split(COLON)[0],
                it -> it.split(COLON)[1]
            ));
    }

    public static Map<String, String> getBody(String body) {
        return parseQuery(Arrays.stream(body.split(AMPERSAND)));
    }

    public static String trimExtensionIfExists(String path) {
        return path.split("//.")[0];
    }

    public static Map<String, String> getQuery(String request) {
        String firstLine = getFirstLine(request);
        if (!firstLine.contains("?")) {
            return Collections.emptyMap();
        }
        return parseQuery(Arrays.stream(firstLine
            .split(SPACE)[1]
            .split(QUESTION_MARK)[1]
            .split(AMPERSAND)));
    }

    private static Map<String, String> parseQuery(Stream<String> queries) {
        return queries.collect(Collectors.toMap(
            it -> it.split(EQUAL)[0],
            it -> it.split(EQUAL)[1]));
    }
}
