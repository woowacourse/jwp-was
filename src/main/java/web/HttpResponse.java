package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dataOutputStream;

    public HttpResponse(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void response200Header(int lengthOfBodyContent) {
        try {
            this.dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            this.dataOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            this.dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            this.dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response200Header(int lengthOfBodyContent, String contentType) {
        try {
            this.dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            this.dataOutputStream.writeBytes("Content-Type: " + contentType + "\r\n");
            this.dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            this.dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302Header(String url) {
        try {
            this.dataOutputStream.writeBytes("HTTP/1.1 302 Found \r\n");
            this.dataOutputStream.writeBytes("Location: " + url + "\r\n");
            this.dataOutputStream.writeBytes("\r\n");
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
