package webserver.http.response;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeader {
    private final HttpResponseLine httpResponseLine;
    private final Map<String, String> headers;

    public HttpResponseHeader(HttpResponseLine httpResponseLine, Map<String, String> headers) {
        this.httpResponseLine = httpResponseLine;
        this.headers = headers;
    }

    public HttpResponseHeader(HttpResponseLine httpResponseLine) {
        this(httpResponseLine, new HashMap<>());
    }

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public String format() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(httpResponseLine.format());
        stringBuilder.append("\n");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(": ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public HttpResponseLine getHttpResponseLine() {
        return httpResponseLine;
    }
}
