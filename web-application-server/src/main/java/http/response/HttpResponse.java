package http.response;

import java.io.DataOutputStream;
import java.io.IOException;

import http.request.HttpRequest;

public class HttpResponse {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String RESPONSE_HEADER_LOCATION = "Location";
    private static final String RESPONSE_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String RESPONSE_HEADER_CONTENT_LENGTH = "Content-Length";
    private static final String RESPONSE_HEADER_SET_COOKIE = "Set-Cookie";
    private static final String RESPONSE_HEADER_ALLOW = "Allow";
    private static final String COOKIE_NAME_PATH = "Path";
    private static final String COOKIE_VALUE_PATH = "/";
    private static final String CHARSET_UTF_8 = ";charset=utf-8";

    private final DataOutputStream dos;
    private final HttpRequest httpRequest;
    private final HttpResponseLine httpResponseLine;
    private final HttpResponseHeader httpResponseHeader;
    private final HttpResponseBody httpResponseBody;

    public HttpResponse(final DataOutputStream dos, final HttpRequest httpRequest) throws CloneNotSupportedException {
        this.dos = dos;
        this.httpRequest = httpRequest;
        this.httpResponseLine = new HttpResponseLine();
        this.httpResponseHeader = new HttpResponseHeader(httpRequest.getCookie());
        this.httpResponseBody = new HttpResponseBody();
    }

    public void response200(final byte[] body) throws IOException {
        this.httpResponseLine.setHttpStatus(HttpStatus.OK);

        String contentType = this.httpRequest.getContentType();
        initHeader(contentType, body.length);

        this.httpResponseBody.setBody(body);
        addCookieInHeader();

        render();
    }

    public void response302(final String redirectUrl) throws IOException {
        this.httpResponseLine.setHttpStatus(HttpStatus.FOUND);

        addHeader(RESPONSE_HEADER_LOCATION, redirectUrl);
        addCookieInHeader();

        render();
    }

    public void response405(final String allowMethods) throws IOException {
        this.httpResponseLine.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);

        addHeader(RESPONSE_HEADER_ALLOW, allowMethods);

        render();
    }

    private void initHeader(final String contentType, final int lengthOfBodyContent) {
        addHeader(RESPONSE_HEADER_CONTENT_TYPE, contentType + CHARSET_UTF_8);
        addHeader(RESPONSE_HEADER_CONTENT_LENGTH, lengthOfBodyContent);
    }

    public void addHeader(final String name, final Object value) {
        this.httpResponseHeader.addResponseHeader(name, value);
    }
    public void addCookie(final String name, final String value) {
        this.httpResponseHeader.addCookie(name, value);
    }

    private void addCookieInHeader() {
        if (this.httpResponseHeader.isEmptyCookie()) {
            return ;
        }

        this.httpResponseHeader.addCookie(COOKIE_NAME_PATH, COOKIE_VALUE_PATH);
        String flatCookies = this.httpResponseHeader.flatCookie();
        addHeader(RESPONSE_HEADER_SET_COOKIE, flatCookies);
    }

    private void render() throws IOException {
        this.httpResponseLine.write(this.dos);
        this.httpResponseHeader.write(this.dos);
        this.dos.writeBytes(NEW_LINE);
        this.httpResponseBody.write(this.dos);
        this.dos.flush();
    }
}
