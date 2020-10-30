package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeader {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String COLON = ": ";

    private final Map<String, Object> responseHeader;

    public HttpResponseHeader() {
        this.responseHeader = new HashMap<>();
    }

    public void addResponseHeader(String key, Object value) {
        this.responseHeader.put(key, value);
    }

    public Object getValue(final String key) {
        return this.responseHeader.get(key);
    }

    public void write(final DataOutputStream dos) throws IOException {
        for (Map.Entry<String, Object> entry : responseHeader.entrySet()) {
            dos.writeBytes(entry.getKey() + COLON + entry.getValue() + NEW_LINE);
        }
    }
}
