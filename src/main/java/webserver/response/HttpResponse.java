package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;
    private int statusCode;
    private Map<String, Object> header;
    private byte[] body;

    public HttpResponse(DataOutputStream dos, int statusCode, Map<String, Object> header, byte[] body) {
        this.dos = dos;
        this.statusCode = statusCode;
        this.header = header;
        this.body = body;
    }

    public void render() {
        if (statusCode == 200) {
            response200Header(dos);
            responseBody(dos, body);
        }

        if (statusCode == 302) {
            response302Header(dos);
        }
    }

    private void response200Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + header.get("Content-Type") + "\r\n");
            dos.writeBytes("Content-Length: " + header.get("lengthOfBodyContent") + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + header.get("location") + "\r\n");
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


