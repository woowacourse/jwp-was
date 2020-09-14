package webserver.dto;

import java.util.Map;

public class Headers {

    private final Map<String, String> headers;

    public Headers(Map<String, String> headers) {
        this.headers = headers;
    }

    public static Headers of(Map<String, String> headers) {
        return new Headers(headers);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
