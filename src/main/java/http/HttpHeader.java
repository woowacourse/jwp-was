package http;

import http.exception.InvalidHeaderFormatException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpHeader {
    public static final String COOKIE = "Cookie";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final String HEADER_DELIMITER = ":";

    private Map<String, String> headers = new HashMap<>();

    public HttpHeader() {
    }

    public HttpHeader(List<String> headers) {
        for (String header : headers) {
            int index = header.indexOf(HEADER_DELIMITER);
            if (index == -1) {
                throw new InvalidHeaderFormatException();
            }
            String key = header.substring(0, index);
            String value = header.substring(index + 1);
            this.headers.put(key.trim(), value.trim());
        }
    }

    public String getValue(String key) {
        return headers.get(key);
    }

    public boolean matchContentType(String contentType) {
        return headers.get(CONTENT_TYPE).equals(contentType);
    }

    public int getContentLength() {
        return Integer.parseInt(headers.getOrDefault(CONTENT_LENGTH_KEY, "-1"));
    }

    public boolean containContentLength() {
        return headers.containsKey(CONTENT_LENGTH_KEY);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Set<Map.Entry<String, String>> getHeaders() {
        return headers.entrySet();
    }
}