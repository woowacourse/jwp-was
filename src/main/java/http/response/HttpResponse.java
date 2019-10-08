package http.response;

import http.HttpVersion;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ServerErrorException;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String BLANK = " ";
    private static final String NEW_LINE = "\r\n";
    private static final String COLON = ": ";

    private StatusCode statusCode;
    private HttpVersion httpVersion;
    private HttpResponseHeader header;
    private byte[] body;

    public HttpResponse(HttpRequest httpRequest) {
        this.httpVersion = httpRequest.getVersion();
        this.header = new HttpResponseHeader(httpRequest.getCookie());
    }

    public void okResponse(String contentType, byte[] body) {
        this.statusCode = StatusCode.OK;
        header.addHeader(CONTENT_TYPE, String.format("text/%s; charset=utf-8", contentType));
        this.body = body;
        header.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
    }

    public void redirect(String location) {
        this.statusCode = StatusCode.FOUND;
        header.addHeader(LOCATION, location);
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
        String line = httpVersion.getVersion() + BLANK
                + statusCode.getStatusValue() + BLANK
                + statusCode.getStatus();
        logger.debug("{}", line + NEW_LINE);
        dos.writeBytes(line + NEW_LINE);
    }

    private void writeHeader(DataOutputStream dos) {
        try {
            for (String line : header.getKeySet()) {
                logger.debug("Response header {}", line + COLON + header.getHeader(line) + NEW_LINE);
                dos.writeBytes(line + COLON + header.getHeader(line) + NEW_LINE);
            }
        } catch (IOException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    private void writeCookie(DataOutputStream dos) {
        if (header.hasCookie()) {
            try {
                dos.writeBytes(SET_COOKIE + COLON + header.getCookieValues() + NEW_LINE);
            } catch (IOException e) {
                throw new ServerErrorException(e.getMessage());
            }
        }
    }

    private void writeBody(DataOutputStream dos) {
        try {
            dos.writeBytes(NEW_LINE);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void write(DataOutputStream dos) throws IOException {
        writeStartLine(dos);
        writeHeader(dos);
        writeCookie(dos);
        writeBody(dos);
    }
}
