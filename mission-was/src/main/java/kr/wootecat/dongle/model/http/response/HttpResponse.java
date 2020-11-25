package kr.wootecat.dongle.model.http.response;

import static com.google.common.base.Charsets.*;
import static com.google.common.net.HttpHeaders.*;
import static kr.wootecat.dongle.model.http.HttpStatus.*;
import static kr.wootecat.dongle.model.http.response.HttpResponseHeaders.*;
import static kr.wootecat.dongle.model.http.response.HttpStatusLine.*;

import kr.wootecat.dongle.model.http.Cookie;
import kr.wootecat.dongle.model.http.HttpStatus;
import kr.wootecat.dongle.model.http.MimeType;

public class HttpResponse {

    private final HttpStatusLine statusLine;
    private final HttpResponseHeaders responseHeaders;
    private byte[] body;

    private HttpResponse(HttpStatusLine httpStatusLine,
            HttpResponseHeaders httpResponseHeaders) {
        this.statusLine = httpStatusLine;
        this.responseHeaders = httpResponseHeaders;
    }

    public static HttpResponse withEmpty(boolean parsedSuccessfully) {
        if (parsedSuccessfully) {
            return with200Empty();
        }
        return with500Empty();
    }

    public static HttpResponse withEmpty(HttpStatus httpStatus) {
        return new HttpResponse(withDefaultVersion(httpStatus), ofEmpty());
    }

    public static HttpResponse with200Empty() {
        return withEmpty(OK);
    }

    private static HttpResponse with500Empty() {
        return withEmpty(INTERNAL_SERVER_ERROR);
    }

    public static HttpResponse withContent(HttpStatus httpStatus, MimeType mimeType, String content) {
        HttpResponse httpResponse = HttpResponse.with200Empty();
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

    public void addCookie(Cookie cookie) {
        responseHeaders.addCookie(cookie);
    }

    public void addBody(byte[] body, MimeType mimeType) {
        this.addHeader(CONTENT_TYPE, mimeType.getMimeType());
        this.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
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

    public Cookie getCookie(String name) {
        return responseHeaders.getCookie(name);
    }
}
