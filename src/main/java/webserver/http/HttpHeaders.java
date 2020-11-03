package webserver.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpHeaders {
    private static final String HEADER_KEY_VALUE_SEPARATOR = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> httpHeaders;

    public HttpHeaders(Map<String, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        for (Map.Entry<String, String> entry : httpHeaders.entrySet()) {
            dataOutputStream.writeBytes(
                entry.getKey() + HEADER_KEY_VALUE_SEPARATOR + entry.getValue() + System.lineSeparator());
        }
    }

    public void put(String key, String value) {
        httpHeaders.put(key, value);
    }

    public boolean hasValue(String key) {
        return httpHeaders.get(key) != null;
    }

    public String getValue(String key) {
        return httpHeaders.get(key);
    }

    public int getContentLength() {
        return Integer.parseInt(httpHeaders.get(CONTENT_LENGTH));
    }
}
