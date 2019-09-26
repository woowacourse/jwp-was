package http.response;

import http.common.Cookie;
import http.common.Cookies;
import http.common.HttpHeader;
import http.common.HttpVersion;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private static final String LOCATION = "LOCATION";
    private static final String NEW_LINE = "\r\n";
    private DataOutputStream dos;
    private StatusLine statusLine = new StatusLine();
    private HttpHeader responseHeader = new HttpHeader();
    private Cookies cookies = new Cookies();
    private ResponseBody responseBody;

    public HttpResponse(final HttpRequest httpRequest, final DataOutputStream dos) {
        this.setHttpVersion(httpRequest.getHttpVersion());
        this.putHeader("Content-Type", httpRequest.findHeader("Content-Type"));
        this.dos = dos;
    }

    public void setHttpVersion(final HttpVersion httpVersion) {
        this.statusLine.setHttpVersion(httpVersion);
    }

    public void addCookie(final Cookie cookie) {
        this.cookies.addCookie(cookie);
    }

    public void setResponseBody(final ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    public int getBodyLength() {
        return responseBody.getLength();
    }

    public void putHeader(String name, String value) {
        responseHeader.putHeader(name, value);
    }

    public void ok() {
        statusLine.setHttpStatus(HttpStatus.OK);
        putHeader("Content-Length",Integer.toString(responseBody.getLength()));
    }

    public void notFound() {
        statusLine.setHttpStatus(HttpStatus.NOT_FOUND);
    }

    public void redirect(final String path) {
        statusLine.setHttpStatus(HttpStatus.FOUND);
        responseHeader.putHeader(LOCATION, path);
    }

    public void response() {
        try {
            writeStatusLine();
            writeHeaders();
            writeCookies();
            writeBody();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void writeStatusLine() throws IOException {
        log.debug("response: {}", statusLine.getVersionAndStatusString());
        dos.writeBytes(statusLine.getVersionAndStatusString() + NEW_LINE);
    }

    private void writeHeaders() throws IOException {
        log.debug("response: {}",responseHeader.getAllHeaderStrings());
        dos.writeBytes(responseHeader.getAllHeaderStrings());
    }

    private void writeCookies() throws IOException {
        log.debug("response: {}",cookies.getAllCookiesString());
        dos.writeBytes(cookies.getAllCookiesString());
    }

    private void writeBody() throws IOException {
        dos.writeBytes(NEW_LINE);
        dos.write(responseBody.getBody(), 0, responseBody.getLength());
        dos.flush();
    }
}
