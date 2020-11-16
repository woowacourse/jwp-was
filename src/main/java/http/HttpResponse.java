package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class HttpResponse {
    private StatusLine statusLine;
    private HttpHeaders headers;
    private String body;

    public HttpResponse(String version) {
        this.statusLine = new StatusLine(version, HttpStatus.OK);
        this.headers = new HttpHeaders();
    }

    public void setStatus(HttpStatus httpStatus) {
        statusLine.update(httpStatus);
    }

    public void addHeader(String headerName, String headerValue) {
        this.headers.add(headerName, headerValue);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void send(DataOutputStream dos) throws IOException {
        dos.writeBytes(statusLine.getStatusLineString() + System.lineSeparator());
        dos.writeBytes(headers.getHttpHeaderString() + System.lineSeparator());
        dos.writeBytes(System.lineSeparator());
        if (Objects.nonNull(body) && !body.isEmpty()) {
            byte[] bytes = body.getBytes();
            dos.write(bytes, 0, bytes.length);
        }
        dos.flush();
    }
}
