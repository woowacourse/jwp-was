package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import http.HttpHeaders;
import http.HttpStatus;

public class HttpResponse {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final DataOutputStream writer;
    private final HttpStatusLine statusLine;
    private final HttpHeaders headers;

    private HttpResponse(DataOutputStream writer, HttpStatusLine statusLine, HttpHeaders headers) {
        this.writer = writer;
        this.statusLine = statusLine;
        this.headers = headers;
    }

    public static HttpResponse from(OutputStream out, String version) {
        DataOutputStream writer = new DataOutputStream(out);
        return new HttpResponse(writer, HttpStatusLine.from(version), HttpHeaders.create());
    }

    public HttpResponse status(HttpStatus status) {
        statusLine.setStatus(status);
        return this;
    }

    public HttpResponse set(String key, String value) {
        headers.set(key, value);
        return this;
    }

    public void end(byte[] body) throws IOException {
        writeStatusLine();
        if (body != null) {
            headers.set("Content-Length", String.valueOf(body.length));
            writeHeaders();
            writeEmptyLine();
            writer.write(body, 0, body.length);
        } else {
            writeHeaders();
            writeEmptyLine();
        }
        writer.flush();
    }

    public void sendRedirect(String to) throws IOException {
        status(HttpStatus.FOUND).set("Location", to).end(null);
    }

    private void writeStatusLine() throws IOException {
        writer.writeBytes(statusLine.build() + LINE_SEPARATOR);
    }

    private void writeHeaders() throws IOException {
        if (headers.isNotEmpty()) {
            writer.writeBytes(headers.build() + LINE_SEPARATOR);
        }
    }

    private void writeEmptyLine() throws IOException {
        writer.writeBytes(LINE_SEPARATOR);
    }
}
