package webserver.http.request;

import java.util.Map;

public class HttpHeader {
    private final Map<String, String> fields;

    public HttpHeader(Map<String, String> fields) {
        this.fields = fields;
    }

    public String get(String key) {
        return fields.get(key);
    }
}
