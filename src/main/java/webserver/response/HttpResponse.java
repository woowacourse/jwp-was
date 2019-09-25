package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class HttpResponse {
    private HttpStatus httpStatus;
    private ResponseHeader header;
    private byte[] body;

    public HttpResponse(HttpStatus httpStatus, ResponseHeader header, byte[] body) {
        this.httpStatus = httpStatus;
        this.header = header;
        this.body = body;
    }

    public static HttpResponse ok(ResponseHeader header, byte[] body) {
        return new HttpResponse(HttpStatus.OK, header, body);
    }

    public static HttpResponse found(ResponseHeader header) {
        return new HttpResponse(HttpStatus.FOUND, header, null);
    }

    public static HttpResponse error(HttpStatus httpStatus, String message) {
        byte[] body = message.getBytes();
        ResponseHeader header = ResponseHeader.error(body.length);
        return new HttpResponse(httpStatus, header, body);
    }

    public void render(DataOutputStream dos) throws IOException {
        responseLine(dos);
        responseHeader(dos);
        if (body != null) {
            responseBody(dos, body);
        }
        responseFlush(dos);
    }

    private void responseFlush(DataOutputStream dos) throws IOException {
        dos.flush();
    }

    private void responseLine(DataOutputStream dos) throws IOException {
        dos.writeBytes("HTTP/1.1 " + httpStatus.getCode() + " " + httpStatus.getName() + "\r\n");
    }

    private void responseHeader(DataOutputStream dos) throws IOException {
        for (Map.Entry value : header.entrySet()) {
            dos.writeBytes(value.getKey() + ": " + value.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpResponse that = (HttpResponse) o;

        if (httpStatus != that.httpStatus) return false;
        if (!Objects.equals(header, that.header)) return false;
        return Arrays.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        int result = httpStatus != null ? httpStatus.hashCode() : 0;
        result = 31 * result + (header != null ? header.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(body);
        return result;
    }
}


