package http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static http.HttpString.*;

public class HttpHeader {
    private static final String NOT_EXIST_BODY = "-1";
    private static final String KEY_VALUE_DELIMITER = ":";

    private Map<String, String> headers = new HashMap<>();

    public HttpHeader() {
    }

    public HttpHeader(List<String> headers) {
        for (String header : headers) {
            int delimiter = header.indexOf(KEY_VALUE_DELIMITER);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            sb.append(header.getKey() + KEY_VALUE_DELIMITER + WHITE_SPACE +
                    header.getValue() + CRLF);
        }
        sb.append(CRLF);

        return sb.toString();
    }
}
