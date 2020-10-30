package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

import http.Cookies;
import http.request.HttpRequest;

public class HttpResponse {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String CONTENT_TYPE_TEXT_HTML = "text/html";
    private static final String RESPONSE_HEADER_LOCATION = "Location";
    private static final String RESPONSE_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String RESPONSE_HEADER_CONTENT_LENGTH = "Content-Length";
    private static final String CHARSET_UTF_8 = ";charset=utf-8";

    private final DataOutputStream dos;
    private final HttpRequest httpRequest;
    private final HttpResponseLine httpResponseLine;
    private final HttpResponseHeader httpResponseHeader;
    private final HttpResponseBody httpResponseBody;
    private final Cookies cookies;

    public HttpResponse(final DataOutputStream dos, final HttpRequest httpRequest) {
        this.dos = dos;
        this.httpRequest = httpRequest;
        this.httpResponseLine = new HttpResponseLine();
        this.httpResponseHeader = new HttpResponseHeader();
        this.httpResponseBody = new HttpResponseBody();
        this.cookies = new Cookies(httpRequest);
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

        int lengthOfBodyContent = Integer.parseInt(this.httpRequest.getHttpRequestHeaderByName(RESPONSE_HEADER_CONTENT_LENGTH));
        initHeader(CONTENT_TYPE_TEXT_HTML, lengthOfBodyContent);

        addHeader(RESPONSE_HEADER_LOCATION, redirectUrl);
        addCookieInHeader();

        render();
    }

    private void initHeader(final String contentType, final int lengthOfBodyContent) {
        addHeader(RESPONSE_HEADER_CONTENT_TYPE, contentType + CHARSET_UTF_8);
        addHeader(RESPONSE_HEADER_CONTENT_LENGTH, lengthOfBodyContent);
    }

    public void addHeader(final String name, final Object value) {
        this.httpResponseHeader.addResponseHeader(name, value);
    }
    public void addCookie(final String name, final Object value) {
        this.cookies.addCookie(name, value);
    }

    private void addCookieInHeader() {
        if (this.cookies.isEmpty()) {
            return ;
        }

        this.cookies.addCookie("Path", "/");
        String flatCookies = this.cookies.flat();
        addHeader("Set-Cookie", flatCookies);
    }

    private void render() throws IOException {
        this.httpResponseLine.write(this.dos);
        this.httpResponseHeader.write(this.dos);
        this.dos.writeBytes(NEW_LINE);
        this.httpResponseBody.write(this.dos);
        this.dos.flush();
    }
}
