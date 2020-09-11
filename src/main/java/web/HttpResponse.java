package web;

import static web.RequestHeader.*;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;
    private byte[] content;

    public HttpResponse(DataOutputStream dos, byte[] content) {
        this.dos = dos;
        this.content = content;
        if (content != null) {
            response200Header();
            responseBody();
        }
    }

    private void response200Header() {
        try {
            dos.writeBytes("HTTP/1.1 200 OK" + NEW_LINE);
            dos.writeBytes("Content-Type: text/html;charset=utf-8" + NEW_LINE);
            dos.writeBytes("Content-Length: " + content.length + NEW_LINE);
            dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody() {
        try {
            dos.write(content, 0, content.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
