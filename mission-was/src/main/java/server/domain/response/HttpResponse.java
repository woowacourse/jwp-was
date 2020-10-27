package server.domain.response;

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
    private final ResponseCookies responseCookies;

    public HttpResponse(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
        this.headerParams = new HashMap<>();
        this.responseCookies = new ResponseCookies();
    }

    private void responseHeader(StatusCode statusCode) {
        try {
            this.dataOutputStream.writeBytes(createResponseLine(statusCode));
            this.dataOutputStream.writeBytes(createResponse(statusCode));
            writeCookies();
            this.dataOutputStream.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String createResponseLine(StatusCode statusCode) {
        return String.format("HTTP/1.1 %d %s\n", statusCode.getStatusCode(), statusCode.getReasonPhrase());
    }

    private String createResponse(StatusCode statusCode) {
        StringBuilder sb = new StringBuilder();
        for (String header : statusCode.getHeaders()) {
            sb.append(header)
                .append(": ")
                .append(headerParams.get(header))
                .append("\n");
        }
        return sb.toString();
    }

    private void writeCookies() throws IOException {
        for (ResponseCookie responseCookie : responseCookies.getResponseCookies()) {
            this.dataOutputStream.writeBytes(responseCookie.convertToString() + NEW_LINE);
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

    public void forward(String content) {
        byte[] body = content.getBytes();
        headerParams.put("Content-Type", StaticFileType.HTML + ";charset=utf-8");
        headerParams.put("Content-Length", String.valueOf(body.length));
        responseHeader(StatusCode.OK);
        responseBody(this.dataOutputStream, body);
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

    public void addCookie(ResponseCookie responseCookie) {
        responseCookies.addCookie(responseCookie);
    }
}
