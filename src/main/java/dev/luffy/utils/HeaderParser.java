package dev.luffy.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeaderParser {

    private static final String HEADER_DELIMITER = ": ";

    public static Map<String, String> parse(List<String> lines) {
        return buildHeaders(lines, new HashMap<>());
    }

    private static Map<String, String> buildHeaders(List<String> lines, Map<String, String> headers) {
        for (String line : lines) {
            String[] splitLine = line.split(HEADER_DELIMITER);
            headers.put(splitLine[0], splitLine[1]);
        }
        return headers;
    }
}
