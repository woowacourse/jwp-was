package http.response;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.RequestHandler;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String NEW_LINE = System.lineSeparator();

    private final DataOutputStream dos;

    public HttpResponse(final DataOutputStream dos) {
        this.dos = dos;
    }

    public void response200Header(final String contentType,final  int lengthOfBodyContent) {
        try {
            this.dos.writeBytes("HTTP/1.1 200 OK" + NEW_LINE);
            this.dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8" + NEW_LINE);
            this.dos.writeBytes("Content-Length: " + lengthOfBodyContent + NEW_LINE);
            this.dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302Header(final int lengthOfBodyContent) {
        try {
            this.dos.writeBytes("HTTP/1.1 302 FOUND" + NEW_LINE);
            this.dos.writeBytes("Content-Type: text/html;charset=utf-8" + NEW_LINE);
            this.dos.writeBytes("Content-Length: " + lengthOfBodyContent + NEW_LINE);
            this.dos.writeBytes("Location: " + "http://localhost:8080/index.html" + NEW_LINE);
            this.dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(final byte[] body) {
        try {
            this.dos.write(body, 0, body.length);
            this.dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
