package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class HttpResponse {
    private static final String NEW_LINE = "\r\n";
    private HttpStatus httpStatus;
    private Map<String, Object> header;
    private byte[] body;

    public HttpResponse(HttpStatus httpStatus, Map<String, Object> header, byte[] body) {
        this.httpStatus = httpStatus;
        this.header = header;
        this.body = body;
    }

    public static HttpResponse ok(Map<String, Object> header, byte[] body) {
        return new HttpResponse(HttpStatus.OK, header, body);
    }

    public static HttpResponse redirect(Map<String, Object> header, byte[] body) {
        return new HttpResponse(HttpStatus.FOUND, header, body);
    }

    public static HttpResponse error(HttpStatus httpStatus) {
        return new HttpResponse(httpStatus, null, null);
    }

    public void send(DataOutputStream dos) throws IOException {
        responseLine(dos);
        responseHeader(dos);
        if (body != null) {
            responseBody(dos, body);
        }
        dos.flush();
    }

    private void responseLine(DataOutputStream dos) throws IOException {
        dos.writeBytes("HTTP/1.1 " + httpStatus.getCode() + " " + httpStatus.getName() + NEW_LINE);
    }

    private void responseHeader(DataOutputStream dos) throws IOException {
        for (Map.Entry value : header.entrySet()) {
            dos.writeBytes(value.getKey() + ": " + value.getValue() + NEW_LINE);
        }
        dos.writeBytes(NEW_LINE);

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


