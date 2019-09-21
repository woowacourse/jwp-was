package http.response;

import http.HttpHeaders;

public class HttpResponse {
    public static final String CRLF = "\r\n";

    private HttpStatusLine statusLine;
    private HttpHeaders headers;
    private byte[] body;

    public HttpResponse(HttpStatusLine statusLine, HttpHeaders headers) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = null;
    }

    public String getHeaderMessage() {
        return statusLine + CRLF
                + headers
                + CRLF;
    }

    public void setBody(byte[] body, String contentType) {
        if (body != null) {
            headers.put("Content-Type", contentType);
            headers.put("Content-Length", Integer.toString(body.length));
            this.body = body;
        }
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
        return (body == null) ? "" : new String(body);
    }

    public boolean hasBody() {
        return body != null;
    }
}
