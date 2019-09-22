package http.request.factory;

import http.request.HttpRequest;
import http.request.HttpRequestHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestHeaderFactory {

    public static HttpRequestHeader create(List<String> lines) {
        Map<String, String> fields = new HashMap<>();
        lines.forEach(line -> {
            String[] element = line.split(": ");
            fields.put(element[0], element[1]);
        });

        return new HttpRequestHeader(fields);
    }
}
