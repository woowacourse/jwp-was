package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {
    private HttpStatus httpStatus;
    private Map<String, Object> header;
    private byte[] body;

    public HttpResponse(HttpStatus httpStatus, Map<String, Object> header, byte[] body) {
        this.httpStatus = httpStatus;
        this.header = header;
        this.body = body;
    }

    public void render(DataOutputStream dos) throws IOException {
        responseHeader(dos);
        if (httpStatus == HttpStatus.OK) {
            responseBody(dos, body);
        }
    }

    private void responseHeader(DataOutputStream dos) throws IOException {
        dos.writeBytes("HTTP/1.1 " + httpStatus.getCode() + " " + httpStatus.getName() + "\r\n");
        for (Map.Entry value : header.entrySet()) {
            dos.writeBytes(value.getKey() + ": " + value.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}


