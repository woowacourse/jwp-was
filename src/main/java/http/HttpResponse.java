package http;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dataOutputStream;

    public HttpResponse(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void response200Header(String contentType, int lengthOfBodyContent) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("Content-Type: " + contentType + "\r\n");
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302Header(String url) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dataOutputStream.writeBytes("Location: " + url + "\r\n");
            dataOutputStream.writeBytes("\r\n");
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
