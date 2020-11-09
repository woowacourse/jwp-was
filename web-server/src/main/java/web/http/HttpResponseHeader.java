package web.http;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeader {

    private HttpResponseLine httpResponseLine;
    private Map<String, Object> headers = new HashMap<>();

    public HttpResponseHeader(HttpResponseLine httpResponseLine) {
        this.httpResponseLine = httpResponseLine;
    }

    public HttpResponseHeader() {}

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHttpResponseLine(HttpResponseLine httpResponseLine) {
        this.httpResponseLine = httpResponseLine;
    }

    public HttpResponseLine getHttpResponseLine() {
        return httpResponseLine;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            stringBuilder.append(entry.getKey())
                .append(": ")
                .append(entry.getValue())
                .append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
