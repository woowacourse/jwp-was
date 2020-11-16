package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import controller.UserController;

public class HttpResponse {
    private static final String DEFAULT_PROTOCOL_VERSION = "HTTP/1.1";
    private static final HttpStatus DEFAULT_STATUS = HttpStatus.OK;

    private StatusLine statusLine;
    private HttpHeaders headers;
    private byte[] body;

    public HttpResponse() {
        this.statusLine = new StatusLine(DEFAULT_PROTOCOL_VERSION, DEFAULT_STATUS);
        this.headers = new HttpHeaders();
    }

    public void setStatus(HttpStatus httpStatus) {
        statusLine.update(httpStatus);
    }

    public void addHeader(String headerName, String headerValue) {
        this.headers.add(headerName, headerValue);
    }

    public void setBody(byte[] body, ContentType contentType) {
        this.body = body;
        addHeader("Content-Type", contentType.getContentType());
        addHeader("Content-Length", String.valueOf(body.length));
    }

    public void setMethodNotAllowed(List<HttpMethod> methods) {
        setStatus(HttpStatus.METHOD_NOT_ALLOWED);
        addHeader("Content-Type", "test/html");
        String allowedMethods = methods.stream().map(HttpMethod::name).collect(Collectors.joining(", "));
        addHeader("Allow", allowedMethods);
        this.body = "<h1>405 Try another method!</h1>".getBytes();
    }

    public void send(DataOutputStream dos) throws IOException {
        dos.writeBytes(statusLine.getStatusLineString() + System.lineSeparator());
        dos.writeBytes(headers.getHttpHeaderString() + System.lineSeparator());
        dos.writeBytes(System.lineSeparator());
        if (Objects.nonNull(body) && body.length != 0) {
            dos.write(body, 0, body.length);
        }
        dos.flush();
    }
}
