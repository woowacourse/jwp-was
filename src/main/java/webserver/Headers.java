package webserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Headers {

    private Map<String, String> headers;

    public Headers() {
        headers = new HashMap<>();
    }

    public Headers(List<String> lines) {
        headers = new HashMap<>();
        parseHeaders(lines);
    }

    private void parseHeaders(List<String> requestHeaders) {
        requestHeaders.stream()
            .map(line -> line.split(": "))
            .forEach(pair -> headers.put(pair[0], pair[1]));
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }
}
