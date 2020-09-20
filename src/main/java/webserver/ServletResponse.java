package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.FileIoUtils;

public class ServletResponse {
    private final StatusCode statusCode;
    private final Map<String, String> attributes;

    public enum StatusCode {
        OK(200), FOUND(302);

        private final int statusCode;

        StatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

    public ServletResponse(StatusCode statusCode, Map<String, String> attributes) {
        this.statusCode = statusCode;
        this.attributes = attributes;
    }

    public ServletResponse(StatusCode statusCode) {
        this.statusCode = statusCode;
        this.attributes = new HashMap<>();
    }

    public void sendResponse(DataOutputStream dos, ServletRequest request, View body) throws IOException {
        String contentType = request.getHeader("Accept");

        dos.writeBytes(String.format("%s %d %s \r\n", request.getProtocolVersion(), statusCode.getStatusCode(),
            statusCode.name()));
        dos.writeBytes(String.format("Content-Type: %s;charset=utf-8\r\n", contentType.split(",")[0]));
        dos.writeBytes("Content-Length: " + body.getLength() + "\r\n");
        dos.writeBytes("\r\n");
        responseBody(dos, body.getView());
    }

    public void sendResponse(DataOutputStream dos, ServletRequest request) throws IOException {
        String contentType = request.getHeader("Accept");

        dos.writeBytes(String.format("%s %d %s \r\n", request.getProtocolVersion(), statusCode.getStatusCode(),
            statusCode.name()));
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("View")) {
                continue;
            }
            dos.writeBytes(String.format("%s: %s\r\n", entry.getKey(), entry.getValue()));
        }
        dos.writeBytes(String.format("Content-Type: %s;charset=utf-8\r\n", contentType.split(",")[0]));

        String view = attributes.getOrDefault("View", null);
        byte[] body = FileIoUtils.loadFileFromClasspath("/" + view + ".html");
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
        dos.writeBytes("\r\n");
        responseBody(dos, body);
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}
