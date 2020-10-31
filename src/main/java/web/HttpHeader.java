package web;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeader {
    private static final String TOKEN_DELIMITER = ": ";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String DELIMITER = ": ";

    private final Map<String, String> headers = new HashMap<>();

    public HttpHeader() {
    }

    public HttpHeader(List<String> lines) {
        for (String line : lines) {
            String[] tokens = line.split(TOKEN_DELIMITER);
            headers.put(tokens[0], tokens[1]);
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getContentLength() {
        String contentLength = headers.get(HeaderName.CONTENT_LENGTH.getName());
        return Integer.parseInt(contentLength);
    }

    public void put(String key, String value) {
        this.headers.put(key, value);
    }

    public String get(String key) {
        return this.getHeaders().get(key);
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            dataOutputStream.writeBytes(entry.getKey() + DELIMITER + entry.getValue() + NEW_LINE);
        }
    }
}
