package web.server.domain.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import web.server.utils.FileIoUtils;
import web.server.utils.StaticFileType;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String NEW_LINE = System.lineSeparator();

    private final DataOutputStream dataOutputStream;
    private final Map<String, String> headerParams;

    public HttpResponse(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
        this.headerParams = new HashMap<>();
    }

    private void response200Header() {
        try {
            this.dataOutputStream.writeBytes("HTTP/1.1 200 OK " + NEW_LINE);
            this.dataOutputStream
                .writeBytes("Content-Type: " + headerParams.get("Content-Type") + ";charset=utf-8" + NEW_LINE);
            this.dataOutputStream.writeBytes("Content-Length: " + headerParams.get("Content-Length") + NEW_LINE);
            this.dataOutputStream.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header() {
        try {
            this.dataOutputStream.writeBytes("HTTP/1.1 302 FOUND " + NEW_LINE);
            this.dataOutputStream.writeBytes("Location: " + headerParams.get("Location") + NEW_LINE);
            this.dataOutputStream.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response404Header() {
        try {
            this.dataOutputStream.writeBytes("HTTP/1.1 404 NOT FOUND" + NEW_LINE);
            this.dataOutputStream.writeBytes(NEW_LINE);
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

    public void forward(String path) {
        String[] split = path.split("\\.");
        StaticFileType contentType = StaticFileType.from(split[split.length - 1]);
        byte[] body = FileIoUtils.loadFileFromRequest(contentType, path);
        headerParams.put("Content-Type", contentType.getContentType());
        headerParams.put("Content-Length", String.valueOf(body.length));

        response200Header();
        responseBody(this.dataOutputStream, body);
    }

    public void sendRedirect(String path) {
        headerParams.put("Location", path);
        response302Header();
    }

    public void respondPageNotFound() {
        response404Header();
    }

    public void respondMethodNotAllowed() {
        response405Header();
    }

    private void response405Header() {
        try {
            this.dataOutputStream.writeBytes("HTTP/1.1 405 Method Not Allowed" + NEW_LINE);
            this.dataOutputStream.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
