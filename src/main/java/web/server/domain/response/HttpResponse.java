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
    private final Cookies cookies;

    public HttpResponse(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
        this.headerParams = new HashMap<>();
        this.cookies = new Cookies();
    }

    private void responseHeader(StatusCode statusCode) {
        try {
            this.dataOutputStream.writeBytes(statusCode.getStatusLine() + NEW_LINE);
            this.dataOutputStream.writeBytes(statusCode.getHeaders(headerParams));
            writeCookies();
            this.dataOutputStream.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeCookies() throws IOException {
        for (Cookie cookie : cookies.getCookies()) {
            this.dataOutputStream.writeBytes(cookie.convertToString() + NEW_LINE);
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

    public void forward(String path, StaticFileType staticFileType) {
        byte[] body = FileIoUtils.loadFileFromRequest(path);
        headerParams.put("Content-Type", staticFileType.getContentType() + ";charset=utf-8");
        headerParams.put("Content-Length", String.valueOf(body.length));

        responseHeader(StatusCode.OK);
        responseBody(this.dataOutputStream, body);
    }

    public void sendRedirect(String path) {
        headerParams.put("Location", path);
        responseHeader(StatusCode.FOUND);
    }

    public void respondPageNotFound() {
        responseHeader(StatusCode.NOT_FOUND);
    }

    public void respondMethodNotAllowed() {
        responseHeader(StatusCode.METHOD_NOT_ALLOWED);
    }

    public void addCookie(Cookie cookie) {
        cookies.addCookie(cookie);
    }
}
