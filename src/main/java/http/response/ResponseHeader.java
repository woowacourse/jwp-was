package http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {

    public static final String REDIRECT_LOCATION = "Location";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String EMPTY_STRING = "";

    private final Map<String, String> headers;

    public ResponseHeader() {
        this.headers = new HashMap<>();
    }

    public ResponseHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addRedirectHeader(String redirectUri) {
        String redirectLocation = redirectUri.replace(REDIRECT_PREFIX, EMPTY_STRING);
        headers.put(REDIRECT_LOCATION, redirectLocation);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }
}
