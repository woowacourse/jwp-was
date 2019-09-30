package http;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String HEADER_DELIMITER = ":\\s+";
    private static final String CRLF = "\r\n";
    private static final String COLON_AND_WHITESPACE = ": ";

    public static final String ACCEPT = "Accept";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String LOCATION = "Location";

    private Map<String, String> headers;

    public HttpHeaders() {
        headers = new LinkedHashMap<>();
    }

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeaders of(List<String> lines) {
        // TODO: 2019-09-23 Header field 포멧에 맞는지 확인해야 하지 않을까?
        Map<String, String> headers = new HashMap<>();

        for (String header : lines) {
            String[] splicedHeader = header.split(HEADER_DELIMITER);
            headers.put(splicedHeader[KEY_INDEX], splicedHeader[VALUE_INDEX]);
        }
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
