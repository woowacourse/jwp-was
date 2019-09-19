package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private HttpStatus httpStatus;
    private Map<String, Object> header;
    private byte[] body;

    public HttpResponse(HttpStatus httpStatus, Map<String, Object> header, byte[] body) {
        this.httpStatus = httpStatus;
        this.header = header;
        this.body = body;
    }

    public void render(DataOutputStream dos) {
        if (httpStatus == HttpStatus.OK) {
            response200Header(dos);
            responseBody(dos, body);
        }

        if (httpStatus == HttpStatus.FOUND) {
            response302Header(dos);
        }
    }

    private void response200Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + header.get("Content-Type") + "\r\n");
            dos.writeBytes("Content-Length: " + header.get("Content-Length") + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + header.get("Location") + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}


