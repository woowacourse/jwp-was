package http.request.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeader {
    private Map<String, String> headers = new HashMap<>();

    public RequestHeader(List<String> lines) {
        parse(lines);
    }

    private void parse(List<String> lines) {
        lines.forEach(index -> {
            String[] parsedData = index.split(": ");
            headers.put(parsedData[0], parsedData[1]);
        });
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getContentLength() {
        return headers.get("Content-Length");
    }
}
