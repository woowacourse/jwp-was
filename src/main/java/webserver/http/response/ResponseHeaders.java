package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class ResponseHeaders {
    private Map<String, String> headers;

    public ResponseHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void write(DataOutputStream dos) throws IOException {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            dos.writeBytes(entry.getKey() + ": " + entry.getValue() + System.lineSeparator());
        }
        dos.writeBytes(System.lineSeparator());
    }
}
