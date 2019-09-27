package http.response;

import http.HttpHeader;
import http.request.Cookie;
import http.request.HttpRequest;
import http.request.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private StatusCode statusCode;
    private HttpVersion httpVersion;
    private HttpHeader header;
    private byte[] body = new byte[]{};

    public HttpResponse(HttpRequest httpRequest) {
        this.httpVersion = httpRequest.getVersion();
    }

    void addHeader(Map<String, String> headers) {
        header = new HttpHeader(headers);
    }

    public void okResponse(String contentType, byte[] body) {
        this.statusCode = StatusCode.OK;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/" + contentType + ";charset=utf-8");
        headers.put("Content-Length", "" + body.length);
        addHeader(headers);
        this.body = body;
    }

    public void redirectResponse(String location) {
        this.statusCode = StatusCode.FOUND;
        Map<String, String> header = new HashMap<>();
        header.put("Location", location);
        addHeader(header);
    }

    public void badRequest() {
        this.statusCode = StatusCode.BAD_REQUEST;
        addHeader(new HashMap<>());
    }

    public void notFound() {
        this.statusCode = StatusCode.NOT_FOUND;
        addHeader(new HashMap<>());
    }

    public void methodNotAllow() {
        this.statusCode = StatusCode.METHOD_NOT_FOUND;
        addHeader(new HashMap<>());
    }

    public void redirectResponseWithCookie(Cookie cookie, String location) {
        this.statusCode = StatusCode.FOUND;
        Map<String, String> header = new HashMap<>();
        header.put("Location", location);
        header.put("Set-Cookie", cookie.build());
        addHeader(header);
    }

    public void internalServerError() {
        this.statusCode = StatusCode.INTERNAL_SERVER_ERROR;
        addHeader(new HashMap<>());
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
}
