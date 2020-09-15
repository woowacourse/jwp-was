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
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("Content-Type:  " + contentType + ";charset=utf-8\r\n");
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response404Header() {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302Header(String location) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 302 Found \r\n");
            dataOutputStream.writeBytes("Location: " + location + "\r\n");
            dataOutputStream.writeBytes("\r\n");
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
