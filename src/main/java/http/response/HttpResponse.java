package http.response;

import http.Cookie;
import http.HttpHeader;
import http.HttpVersion;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";
    private static final String SET_COOKIE = "Set-Cookie";

    private StatusCode statusCode;
    private HttpVersion httpVersion;
    private HttpHeader header = new HttpHeader();
    private byte[] body = new byte[]{};
    private Cookie cookie = new Cookie();

    public HttpResponse(HttpRequest httpRequest) {
        this.httpVersion = httpRequest.getVersion();
    }

    public void addCookie(String name, String value) {
        cookie.addCookie(name, value);
    }

    public void okResponse(String contentType, byte[] body) {
        this.statusCode = StatusCode.OK;
        header.addHeader(CONTENT_TYPE, String.format("text/%s; charset=utf-8", contentType));
        header.addHeader(CONTENT_LENGTH,  Integer.toString(body.length));
        hasCookie();
        this.body = body;
    }

    public void redirect(String location) {
        this.statusCode = StatusCode.FOUND;
        header.addHeader(LOCATION, location);
        hasCookie();
    }

    public void badRequest() {
        this.statusCode = StatusCode.BAD_REQUEST;
    }

    public void notFound() {
        this.statusCode = StatusCode.NOT_FOUND;
    }

    public void methodNotAllow() {
        this.statusCode = StatusCode.METHOD_NOT_FOUND;
    }

    public void internalServerError() {
        this.statusCode = StatusCode.INTERNAL_SERVER_ERROR;
    }

    private void hasCookie() {
        if (!cookie.isEmpty()) {
            header.addHeader(SET_COOKIE, cookie.build());
        }
    }

    private void writeStartLine(DataOutputStream dos) throws IOException {
        String line = httpVersion.getVersion() + " "
                + statusCode.getStatusValue() + " "
                + statusCode.getStatus();
        logger.debug("{}", line + "\r\n");
        dos.writeBytes(line + "\r\n");
    }

    private void writeHeader(DataOutputStream dos) {
        try {
            for (String line : header.getKeySet()) {
                logger.debug("header {}", line + ": " + header.getHeader(line) + "\r\n");
                dos.writeBytes(line + ": " + header.getHeader(line) + "\r\n");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeBody(DataOutputStream dos) {
        try {
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void write(DataOutputStream dos) throws IOException {
        writeStartLine(dos);
        writeHeader(dos);
        writeBody(dos);
    }
}
