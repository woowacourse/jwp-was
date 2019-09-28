package http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static http.HttpString.CONTENT_LENGTH_KEY;

public class HttpHeader {
    private static final String NOT_EXIST_BODY = "-1";

    private Map<String, String> headers = new HashMap<>();

    public HttpHeader() {
    }

    public HttpHeader(List<String> headers) {
        for (String header : headers) {
            int delimiter = header.indexOf(":");
            String key = header.substring(0, delimiter);
            String value = header.substring(delimiter + 1);
            this.headers.put(key.trim(), value.trim());
        }
    }

    public String getValue(String key) {
        return headers.get(key);
    }

    public int getContentLength() {
        return Integer.parseInt(headers.getOrDefault(CONTENT_LENGTH_KEY, NOT_EXIST_BODY));
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Set<Map.Entry<String, String>> getHeaders() {
        return headers.entrySet();
    }
}
