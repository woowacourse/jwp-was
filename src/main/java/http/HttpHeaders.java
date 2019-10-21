package http;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {
    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final int SPLICE_HEADER_SIZE = 2;
    private static final String KEY_VALUE_DELIMETER = ":\\s+";
    private static final String CRLF = "\r\n";
    private static final String COLON_AND_WHITESPACE = ": ";

    public static final String ACCEPT = "Accept";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String LOCATION = "Location";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String COOKIE = "Cookie";

    private Map<String, String> headers;

    public HttpHeaders() {
        headers = new LinkedHashMap<>();
    }

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeaders parse(List<String> lines) {
        Map<String, String> headers = new HashMap<>();

        lines.stream()
                .map(header -> header.split(KEY_VALUE_DELIMETER))
                .filter(spliced -> spliced.length == SPLICE_HEADER_SIZE)
                .forEach(spliced -> headers.put(spliced[KEY], spliced[VALUE]));
        return new HttpHeaders(headers);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String put(String key, String value) {
        return headers.put(key, value);
    }

    public boolean hasContentLength() {
        return headers.get(CONTENT_LENGTH) != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String key : headers.keySet()) {
            sb.append(key).append(COLON_AND_WHITESPACE)
                    .append(headers.get(key)).append(CRLF);
        }
        return sb.toString();
    }
}
