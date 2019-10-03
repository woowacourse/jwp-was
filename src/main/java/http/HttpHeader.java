package http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpHeader {
    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String CONTENT_LENGTH_KEY = "Content-Length";
    public static final String LOCATION_KEY = "Location";
    public static final String COOKIE_KEY = "Cookie";

    public static final String QUERY_STRING_CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String JSESSIONID = "JSESSIONID";

    public static final String HEADER_DELIMITER = ":";
    public static final String KEY_VALUE_DELIMITER = "=";
    public static final String VALUES_DELIMITER = ";";
    public static final String NOT_EXIST_BODY = "-1";
    private static final String EMPTY = "";

    private Map<String, String> headers = new HashMap<>();

    public HttpHeader() {
    }

    public HttpHeader(List<String> headers) {
        for (String header : headers) {
            int delimiter = header.indexOf(HEADER_DELIMITER);
            String key = header.substring(0, delimiter);
            String value = header.substring(delimiter + 1);
            this.headers.put(key.trim(), value.trim());
        }
    }

    public String getValue(String key) {
        return headers.getOrDefault(key, EMPTY);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            sb.append(String.format("%s%s %s\r\n", header.getKey(),
                    HEADER_DELIMITER, header.getValue()));
        }
        sb.append("\r\n");

        return sb.toString();
    }
}
