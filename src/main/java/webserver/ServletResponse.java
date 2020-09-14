package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
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

        public static StatusCode of(int statusCode) {
            return Arrays.stream(values())
                .filter(value -> value.getStatusCode() == statusCode)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
        }

        public int getStatusCode() {
            return statusCode;
        }

    }

    public ServletResponse(StatusCode statusCode, Map<String, String> attributes) {
        this.statusCode = statusCode;
        this.attributes = attributes;
    }

    public ServletResponse(int code, Map<String, String> attributes) {
        this.statusCode = StatusCode.of(code);
        this.attributes = attributes;
    }

    public void createResponse(DataOutputStream dos, ServletRequest request) throws IOException {
        byte[] body = FileIoUtils.loadFileFromClasspath(request.getPath());
        String contentType = request.getHeader("Accept");

        dos.writeBytes(String.format("%s %d %s \r\n", request.getProtocolVersion(), statusCode.getStatusCode(),
            statusCode.name()));
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            dos.writeBytes(String.format("%s: %s\r\n", entry.getKey(), entry.getValue()));
        }
        dos.writeBytes(String.format("Content-Type: %s\r\n", contentType));
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
        responseBody(dos, body);
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}
