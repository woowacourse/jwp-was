package webserver.servlet.http.response;

import static com.google.common.base.Charsets.*;
import static com.google.common.net.HttpHeaders.*;
import static webserver.servlet.http.response.HttpStatus.*;

import com.google.common.net.HttpHeaders;
import webserver.servlet.http.MimeType;

public class HttpResponse {

    private final HttpStatusLine statusLine;
    private final HttpResponseHeaders responseHeaders;
    private byte[] body;

    public HttpResponse() {
        this.statusLine = HttpStatusLine.withDefaultVersion();
        this.responseHeaders = HttpResponseHeaders.ofEmpty();
    }

    public static HttpResponse withContent(HttpStatus httpStatus, MimeType mimeType, String content) {
        HttpResponse httpResponse = new HttpResponse();
        byte[] body = content.getBytes(UTF_8);
        httpResponse.changeHttpStatus(httpStatus);
        httpResponse.addBody(body, mimeType);
        return httpResponse;
    }

    public void sendRedirect(String urlPath) {
        changeHttpStatus(FOUND);
        addHeader(LOCATION, urlPath);
    }

    public void changeHttpStatus(HttpStatus httpStatus) {
        this.statusLine.changeHttpStatus(httpStatus);
    }

    public void addHeader(String key, String value) {
        this.responseHeaders.addHeader(key, value);
    }

    public void addBody(byte[] body, MimeType mimeType) {
        this.addHeader(HttpHeaders.CONTENT_TYPE, mimeType.getMimeType());
        this.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length));
        this.body = body;
    }

    public HttpStatusLine getStatusLine() {
        return statusLine;
    }

    public HttpResponseHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public byte[] getBody() {
        return body;
    }
}
