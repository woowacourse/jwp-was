package http.common;

import java.util.Map;

public class HttpHeader {

    private Map<String, String> headers;

    public HttpHeader(final Map<String, String> headers) {
        this.headers = headers;
    }

    public String findHeader(String name) {
        return headers.get(name);
    }
}
