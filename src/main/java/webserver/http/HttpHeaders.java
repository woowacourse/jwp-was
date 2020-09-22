package webserver.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpHeaders {
    private static final String HEADER_KEY_VALUE_SEPARATOR = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> requestHeaders;

    public HttpHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
            dataOutputStream.writeBytes(
                entry.getKey() + HEADER_KEY_VALUE_SEPARATOR + entry.getValue() + System.lineSeparator());
        }
    }

    public String getValue(String key) {
        if (!requestHeaders.containsKey(key)) {
            throw new IllegalArgumentException("해당 key의 header 값이 없습니다.");
        }
        return requestHeaders.get(key);
    }

    public int getContentLength() {
        return Integer.parseInt(requestHeaders.get(CONTENT_LENGTH));
    }
}
