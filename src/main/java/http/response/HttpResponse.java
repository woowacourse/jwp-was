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
import java.util.Map.Entry;
import java.util.Set;

public class HttpResponse {


    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    public static final String HEADER_DELIMITER = ": ";
    public static final String EQUAL = "=";
    public static final String SET_COOKIE = "Set-Cookie: ";
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
        responseHeader.putHeader(CONTENT_TYPE, httpRequest.getContentType());
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

    public String getHeader(String name) {
        return responseHeader.getHeader(name);
    }

    public void addCookie(Cookie cookie) {
        cookies.addCookie(cookie);
    }

    public void forward(ResponseBody responseBody) {
        statusLine.setHttpStatus(HttpStatus.OK);
        responseHeader.putHeader(CONTENT_LENGTH, Integer.toString(responseBody.getLength()));
        this.responseBody = responseBody;
    }

    public void redirect(final String location) {
        statusLine.setHttpStatus(HttpStatus.FOUND);
        responseHeader.putHeader(LOCATION, location);
    }

    public void error() {
        statusLine.setHttpStatus(HttpStatus.NOT_FOUND);
    }

    public void response() {
        try {
            writeStatusLine();
            writeHeaders();
            writeBody();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void writeStatusLine() throws IOException {
        dos.writeBytes(statusLine.getVersionAndStatusString() + NEW_LINE);
    }

    private void writeHeaders() throws IOException {
        Set<Entry<String, String>> headers = responseHeader.entrySet();
        for (Entry<String, String> header : headers) {
            dos.writeBytes(header.getKey() + HEADER_DELIMITER + header.getValue() + NEW_LINE);
        }
        if (cookies.hasCookie()) {
            dos.writeBytes(getCookiesString());
        }
    }

    private String getCookiesString() {
        StringBuilder stringBuilder = new StringBuilder();
        cookies.toEntrySet().forEach(cookie -> {
            stringBuilder.append(SET_COOKIE)
                .append(cookie.getKey())
                .append(EQUAL)
                .append(cookie.getValue())
                .append(NEW_LINE);
        });
        return stringBuilder.toString();
    }

    private void writeBody() throws IOException {
        if (responseBody != null) {
            dos.writeBytes(NEW_LINE);
            dos.write(responseBody.getBody(), 0, responseBody.getLength());
        }
        dos.flush();
    }


}
