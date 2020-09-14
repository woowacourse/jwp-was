package webserver.http.response;

import java.util.Map;

public class ResponseHeaders {
    private Map<String, String> headers;

    public ResponseHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
