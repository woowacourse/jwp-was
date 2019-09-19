package http.response;

import http.HttpHeaders;
import http.HttpVersion;

import java.util.LinkedHashMap;

import static http.HttpVersion.HTTP_1_1;
import static http.response.HttpStatus.OK;

public class HttpResponse {
    public static final String CRLF = "\r\n";
    private static final HttpVersion DEFAULT_VERSION = HTTP_1_1;
    private static final HttpStatus DEFAULT_STATUS = OK;

    private HttpStatusLine statusLine;
    private HttpHeaders headers;
    private byte[] body;

    public HttpResponse() {
        statusLine = new HttpStatusLine(DEFAULT_VERSION, DEFAULT_STATUS);
        headers = new HttpHeaders(new LinkedHashMap<>());
    }

    public void changeStatusToFound(String redirectPath) {
        this.statusLine.changeStatus(HttpStatus.FOUND);
        this.headers.put("Location", "http://localhost:8080/" + redirectPath);
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setBody(byte[] body, String contentType) {
        this.body = body;
        headers.put("Content-Type", contentType);
        headers.put("Content-Length", Integer.toString(body.length));
    }

    public String getHeaders() {
        return statusLine + CRLF +
                headers + CRLF;
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public String toString() {
        return statusLine + CRLF +
                headers + CRLF +
                body;
    }
}
