package http.request.factory;

import http.request.HttpRequestHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestHeaderFactory {
    private static final int KEY = 0;
    private static final int VALUE = 1;

    public static HttpRequestHeader create(List<String> lines) {
        Map<String, String> fields = new HashMap<>();
        lines.forEach(line -> {
            String[] element = line.split(": ");
            fields.put(element[KEY], element[VALUE]);
        });

        return new HttpRequestHeader(fields);
    }
}
