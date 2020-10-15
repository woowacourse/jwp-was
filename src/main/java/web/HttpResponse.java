package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String NEW_LINE = "\r\n";

    private final DataOutputStream dataOutputStream;

    public HttpResponse(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void response200Header(int lengthOfBodyContent, String contentType) {
        try {
            this.dataOutputStream.writeBytes("HTTP/1.1 200 OK " + NEW_LINE);
            this.dataOutputStream.writeBytes("Content-Type: " + contentType + NEW_LINE);
            this.dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + NEW_LINE);
            this.dataOutputStream.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302Header(String url) {
        try {
            this.dataOutputStream.writeBytes("HTTP/1.1 302 Found " + NEW_LINE);
            this.dataOutputStream.writeBytes("Location: " + url + NEW_LINE);
            this.dataOutputStream.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            this.dataOutputStream.write(body, 0, body.length);
            this.dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
