package webserver;

import java.util.Map;
import java.util.Set;

public class Response {

    private final int statusCode;
    private final String statusText;
    private final Map<String, String> headers;
    private final byte[] body;

    public Response(int statusCode, String statusText, Map<String, String> headers, byte[] body) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.headers = headers;
        this.body = body;
    }

    public Set<String> getHeaderKeys() {
        return headers.keySet();
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public byte[] getBody() {
        return body;
    }
}
