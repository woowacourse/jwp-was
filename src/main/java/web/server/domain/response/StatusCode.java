package web.server.domain.response;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public enum StatusCode {
    OK("OK", 200, Arrays.asList("Content-Type", "Content-Length")),
    FOUND("Found", 302, Arrays.asList("Location")),
    NOT_FOUND("Not Found", 404),
    METHOD_NOT_ALLOWED("Method Not Allowed", 405);

    private final String reasonPhrase;
    private final int statusCode;
    private final List<String> headers;

    StatusCode(String reasonPhrase, int statusCode, List<String> headers) {
        this.reasonPhrase = reasonPhrase;
        this.statusCode = statusCode;
        this.headers = headers;
    }

    StatusCode(String reasonPhrase, int statusCode) {
        this(reasonPhrase, statusCode, Collections.emptyList());
    }

    public String getStatusLine() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1").append(" ")
            .append(this.statusCode).append(" ")
            .append(this.reasonPhrase).append(" ");
        return sb.toString();
    }

    public String getHeaders(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (String header : headers) {
            sb.append(header).append(": ").append(map.get(header)).append("\n");
        }
        return sb.toString();
    }
}
