package webserver.http.request;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestHeader {
    private final HttpRequestLine httpRequestLine;
    private final Map<String, String> headers;

    public HttpRequestHeader(HttpRequestLine httpRequestLine, Map<String, String> headers) {
        this.httpRequestLine = httpRequestLine;
        this.headers = headers;
    }

    public HttpRequestHeader(HttpRequestLine httpRequestLine) {
        this(httpRequestLine, new HashMap<>());
    }

    public HttpRequestHeader(List<String> lines) throws IOException {
        this.httpRequestLine = new HttpRequestLine(lines.get(0));
        this.headers = new HashMap<>();
        for (int i = 1; i < lines.size(); i++) {
            String[] splited = lines.get(i).split(": ");
            if (splited.length >= 2) {
                headers.put(splited[0], splited[1]);
            }
        }
    }

    public HttpRequestLine getHttpRequestLine() {
        return httpRequestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
