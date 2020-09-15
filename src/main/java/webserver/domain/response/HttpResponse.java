package webserver.domain.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.StaticFileType;
import webserver.RequestHandler;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String NEW_LINE = System.lineSeparator();

    private final DataOutputStream dataOutputStream;
    private final Map<String, String> headerParams;

    public HttpResponse(OutputStream outputStream) {
        this.dataOutputStream = new DataOutputStream(outputStream);
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    // 응답을 보낼 때 HTML, CSS, 자바스크립트 파일을 직접 읽어 응답으로 보내는 메소드
    public void forward(String path) {
        String[] split = path.split("\\.");
        StaticFileType contentType = StaticFileType.from(split[split.length - 1]);
        byte[] body = FileIoUtils.loadFileFromRequest(contentType, path);
        headerParams.put("Content-Type", contentType.getContentType());
        headerParams.put("Content-Length", String.valueOf(body.length));

        response200Header();
        responseBody(this.dataOutputStream, body);
    }

    // 다른 URL로 리다이렉트하는 메소드
    public void sendRedirect(String path) {
        headerParams.put("Location", path);
        response302Header();
    }
}
