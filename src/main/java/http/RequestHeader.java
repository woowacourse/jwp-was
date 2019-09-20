package http;

import java.util.Map;

public class RequestHeader {

    private Map<String, String> headers;

    public RequestHeader(final Map<String, String> headers) {
        this.headers = headers;
    }

    public String findHeader(String name) {
        return headers.get(name);
    }
}
