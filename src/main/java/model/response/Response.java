package model.response;

import java.util.Map;
import model.general.Header;

public class Response {

    private final StatusLine statusLine;
    private final Map<Header, String> headers;
    private final byte[] body;

    public Response(StatusLine statusLine, Map<Header, String> headers, byte[] body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public boolean hasContents() {
        return headers.containsKey(Header.CONTENT_LENGTH);
    }

    public String getHttpVersion() {
        return statusLine.getHttpVersion();
    }

    public String getStatusCode() {
        return statusLine.getStatusCode();
    }

    public String getReasonPhrase() {
        return statusLine.getReasonPhrase();
    }

    public Map<Header, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }
}
