package webserver.response;

import java.util.Map;

public class ServletResponse {
    private final Map<String, String> headers;

    public ServletResponse(final Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
