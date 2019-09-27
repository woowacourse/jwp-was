package http.response;

import http.common.Cookie;
import http.common.Cookies;
import http.common.HttpHeader;
import http.common.HttpVersion;
import http.request.HttpRequest;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private static final String LOCATION = "Location";
    private static final String NEW_LINE = "\r\n";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private DataOutputStream dos;
    private StatusLine statusLine = new StatusLine();
    private HttpHeader responseHeader = new HttpHeader();
    private Cookies cookies = new Cookies();
    private ResponseBody responseBody;

    public HttpResponse() {
    }

    public HttpResponse(final HttpRequest httpRequest, final DataOutputStream dos) {
        this.setHttpVersion(httpRequest.getHttpVersion());
        this.dos = dos;

        this.putHeader(CONTENT_TYPE, new Tika().detect(httpRequest.getClassPath()));
    }

    private void setHttpVersion(HttpVersion httpVersion) {
        this.statusLine.setHttpVersion(httpVersion);
    }

    public HttpStatus getHttpStatus() {
        return this.statusLine.getHttpStatus();
    }

    public ResponseBody getResponseBody() {
        return this.responseBody;
    }

    public void addCookie(final Cookie cookie) {
        this.cookies.addCookie(cookie);
    }

    public void putHeader(String name, String value) {
        responseHeader.putHeader(name, value);
    }

    public String getHeader(String name) {
        return responseHeader.findHeader(name);
    }

    public void ok(ResponseBody responseBody) {
        statusLine.setHttpStatus(HttpStatus.OK);
        putHeader(CONTENT_LENGTH, Integer.toString(responseBody.getLength()));
        this.responseBody = responseBody;
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
        dos.writeBytes(statusLine.getVersionAndStatusString() + NEW_LINE);
    }

    private void writeHeaders() throws IOException {
        dos.writeBytes(responseHeader.getAllHeaderStrings());
    }

    private void writeCookies() throws IOException {
        dos.writeBytes(cookies.getAllCookiesString());
    }

    private void writeBody() throws IOException {
        dos.writeBytes(NEW_LINE);
        dos.write(responseBody.getBody(), 0, responseBody.getLength());
        dos.flush();
    }
}
