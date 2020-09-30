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
        return requestHeaders.get(key);
    }

    public String getValueOrDefault(String key, String defaultValue) {
        return requestHeaders.getOrDefault(key, defaultValue);
    }

    public int getContentLength() {
        return Integer.parseInt(requestHeaders.get(CONTENT_LENGTH));
    }
}
