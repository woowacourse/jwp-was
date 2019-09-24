package http.response;

import http.HttpHeader;
import http.request.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private HttpResponseStartLine httpResponseStartLine;
    private HttpHeader header;
    private byte[] body;

    public HttpResponse() {
    }

    void addHeader(Map<String, String> headers) {
        header = new HttpHeader(headers);
    }

    public void okResponse(String contentType, byte[] body) {
        this.httpResponseStartLine = new HttpResponseStartLine(StatusCode.OK, HttpVersion.HTTP_1_1);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/" + contentType + ";charset=utf-8");
        headers.put("Content-Length", "" + body.length);
        addHeader(headers);
        this.body = body;
    }

    public void redirectResponse(String location) {
        this.httpResponseStartLine = new HttpResponseStartLine(StatusCode.FOUND, HttpVersion.HTTP_1_1);
        Map<String, String> header = new HashMap<>();
        header.put("Location", location);
        addHeader(header);
        this.body = new byte[]{};
    }

    public void badRequest() {
        this.httpResponseStartLine = new HttpResponseStartLine(StatusCode.BAD_REQUEST, HttpVersion.HTTP_1_1);
        addHeader(new HashMap<>());
        this.body = new byte[]{};
    }

    public void notFound() {
        this.httpResponseStartLine = new HttpResponseStartLine(StatusCode.NOT_FOUND, HttpVersion.HTTP_1_1);
        addHeader(new HashMap<>());
        this.body = new byte[]{};
    }

    public void methodNotAllow() {
        this.httpResponseStartLine = new HttpResponseStartLine(StatusCode.METHOD_NOT_FOUND, HttpVersion.HTTP_1_1);
        addHeader(new HashMap<>());
        this.body = new byte[]{};
    }

    public void internalServerError() {
        this.httpResponseStartLine = new HttpResponseStartLine(StatusCode.INTERNAL_SERVER_ERROR, HttpVersion.HTTP_1_1);
        addHeader(new HashMap<>());
        this.body = new byte[]{};
    }

    private void writeStartLine(DataOutputStream dos) throws IOException {
        logger.debug("{}", httpResponseStartLine.convertLineToString() + "\r\n");
        String line = httpResponseStartLine.convertLineToString();
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
