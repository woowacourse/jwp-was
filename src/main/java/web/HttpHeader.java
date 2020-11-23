package web;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    public static final String ACCEPT = "Accept";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String LOCATION = "Location";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String COOKIE = "Cookie";
    public static final String HEADER_DELIMITER = ": ";

    private final Map<String, String> headers;

    public HttpHeader() {
        this.headers = new HashMap<>();
    }

    public HttpHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeaderByKey(String key) {
        return headers.get(key);
    }

    public String getAcceptType() {
        String acceptInfo = headers.get(ACCEPT);

        return acceptInfo.split(",")[0];
    }

    public int getContentLength() {
        String contentLength = headers.get(CONTENT_LENGTH);
        return Integer.parseInt(contentLength);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder.append(key)
                    .append(HEADER_DELIMITER)
                    .append(value)
                    .append(" \r\n");
        }
        return stringBuilder.toString();
    }
}
