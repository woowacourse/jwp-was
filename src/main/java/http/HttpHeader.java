package http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpHeader {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final String HEADER_DELIMITER = ":";

    private Map<String, String> headers = new HashMap<>();

    public HttpHeader() {
    }

    public HttpHeader(List<String> headers) {
        for (String header : headers) {
            String[] keyValue = header.split(HEADER_DELIMITER);
            this.headers.put(keyValue[0].trim(), keyValue[1].trim());
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