package network;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    private Map<String, String> headers;

    public HttpHeader() {
        this.headers = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public String toString() {
        return "HttpHeader{" +
                "headers=" + headers +
                '}';
    }
}
