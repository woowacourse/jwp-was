package http.response;

import http.HttpHeaders;

public class HttpResponse {
    public static final String CRLF = "\r\n";
    private static final String EMPTY = "";

    private HttpStatusLine statusLine;
    private HttpHeaders headers;
    private byte[] body;

    HttpResponse(HttpStatusLine statusLine, HttpHeaders headers, byte[] body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public String getHeaderMessage() {
        return statusLine + CRLF
                + headers
                + CRLF;
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public String toString() {
        return statusLine + CRLF
                + headers
                + CRLF
                + getBodyMessage();
    }

    private String getBodyMessage() {
        return !hasBody() ? EMPTY : new String(body);
    }

    public boolean hasBody() {
        return body != null;
    }
}
