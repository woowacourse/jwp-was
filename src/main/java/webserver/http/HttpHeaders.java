package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeaders {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String LOCATION = "Location";
    public static final String HOST = "Host";
    public static final String CONNECTION = "Connection";
    public static final String COOKIE = "Cookie";
    public static final String ACCEPT = "Accept";
    public static final String SET_COOKIE = "Set-Cookie";

    private final Map<String, String> headers;

    public HttpHeaders() {
        headers = new HashMap<>();
    }

    public HttpHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    public void put(final String name, final String value) {
        headers.put(name, value);
    }

    public String get(final String name) {
        return headers.get(name);
    }

    public int size() {
        return headers.size();
    }

    public Set<String> keySet(){
        return headers.keySet();
    }

    public boolean contains(final String name) {
        return headers.containsKey(name);
    }
}
