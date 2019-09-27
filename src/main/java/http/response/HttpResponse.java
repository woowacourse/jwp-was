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
    private StatusCode statusCode;
    private HttpVersion httpVersion;
    private HttpHeader header = new HttpHeader();
    private byte[] body = new byte[]{};
    private Cookie cookie = new Cookie();

    public HttpResponse(HttpRequest httpRequest) {
        this.httpVersion = httpRequest.getVersion();
    }

    public void okResponse(String contentType, byte[] body) {
        this.statusCode = StatusCode.OK;
        header.addHeader("Content-Type", "text/" + contentType + ";charset=utf-8");
        header.addHeader("Content-Length", "" + body.length);
        if (!cookie.isEmpty()) {
            header.addHeader("Set-Cookie", cookie.build());
        }
        this.body = body;
    }

    public void redirect(String location) {
        this.statusCode = StatusCode.FOUND;
        header.addHeader("Location", location);
        if (!cookie.isEmpty()) {
            header.addHeader("Set-Cookie", cookie.build());
        }
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

    public void forward(DataOutputStream dos) throws IOException {
        writeStartLine(dos);
        writeHeader(dos);
        writeBody(dos);
    }

    public void addCookie(String name, String value) {
        cookie.addCookie(name, value);
    }
}
