package http.common;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {

    private Map<String, String> headers;

    public HttpHeader() {
        this.headers = new HashMap<>();
    }

    public HttpHeader(final Map<String, String> headers) {
        this.headers = headers;
    }

    public String findHeader(String name) {
        return headers.get(name);
    }

    public void putHeader(final String name, final String value) {
        headers.put(name, value);
    }
}
