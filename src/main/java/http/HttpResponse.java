package http;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final DataOutputStream dataOutputStream;

    public HttpResponse(final DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void response200Header(String contentType, int lengthOfBodyContent) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK " + LINE_SEPARATOR);
            dataOutputStream.writeBytes("Content-Type:  " + contentType + ";charset=utf-8" + LINE_SEPARATOR);
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + LINE_SEPARATOR);
            dataOutputStream.writeBytes(LINE_SEPARATOR);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302Header(String location) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 302 Found " + System.lineSeparator());
            dataOutputStream.writeBytes("Location: " + location + System.lineSeparator());
            dataOutputStream.writeBytes(LINE_SEPARATOR);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response404Header() {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 404 Not Found " + LINE_SEPARATOR);
            dataOutputStream.writeBytes(LINE_SEPARATOR);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response500Header() {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 500 Internal Server Error " + LINE_SEPARATOR);
            dataOutputStream.writeBytes(LINE_SEPARATOR);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void ok(byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void noContent() {
        try {
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
