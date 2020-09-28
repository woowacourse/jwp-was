package http;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dataOutputStream;

    public HttpResponse(final DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void response200Header(String contentType, int lengthOfBodyContent) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK " + System.lineSeparator());
            dataOutputStream.writeBytes("Content-Type:  " + contentType + ";charset=utf-8" + System.lineSeparator());
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + System.lineSeparator());
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302Header(String location) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 302 Found " + System.lineSeparator());
            dataOutputStream.writeBytes("Location: " + location + System.lineSeparator());
            dataOutputStream.writeBytes(System.lineSeparator());
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response404Header() {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 404 Not Found " + System.lineSeparator());
            dataOutputStream.writeBytes(System.lineSeparator());
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response500Header() {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 500 Internal Server Error " + System.lineSeparator());
            dataOutputStream.writeBytes(System.lineSeparator());
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
