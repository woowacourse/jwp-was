package webserver.http.request;

import static webserver.http.HttpHeaderFields.*;

import java.util.Map;

public class HttpHeader {
    private final Map<String, String> fields;

    public HttpHeader(Map<String, String> fields) {
        this.fields = fields;
    }

    public String get(String key) {
        return fields.get(key);
    }

    public void put(String key, String value) {
        fields.put(key, value);
    }

    public boolean hasSetCookie() {
        return fields.containsKey(SET_COOKIE);
    }
}
