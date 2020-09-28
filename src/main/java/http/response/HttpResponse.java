package http.response;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.RequestHandler;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String NEW_LINE = System.lineSeparator();

    private DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void response200Header(String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK" + NEW_LINE);
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8" + NEW_LINE);
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + NEW_LINE);
            dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 302 OK" + NEW_LINE);
            dos.writeBytes("Content-Type: text/html;charset=utf-8" + NEW_LINE);
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + NEW_LINE);
            dos.writeBytes("Location: " + "http://localhost:8080/index.html" + NEW_LINE);
            dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
