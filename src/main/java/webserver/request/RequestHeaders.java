package webserver.request;

import webserver.HttpField;

import java.util.Map;

public class RequestHeaders {
    private final Map<String, String> headers;

    public RequestHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean isNull(HttpField httpField) {
        return headers.get(httpField.get()) == null;
    }

    public String getValue(String key) {
        return headers.get(key);
    }
}
