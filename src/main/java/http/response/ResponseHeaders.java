package http.response;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseHeaders {

    private Map<String, String> responseHeaders = new LinkedHashMap<>();


    public void addResponseHeaders(String key, String value) {
        responseHeaders.put(key, value);
    }

    public void append(StringBuilder sb) {
        if (!responseHeaders.isEmpty()) {
            for (String header : responseHeaders.keySet()) {
                sb.append(header).append(responseHeaders.get(header)).append("\r\n");
            }
        }

    }
}
