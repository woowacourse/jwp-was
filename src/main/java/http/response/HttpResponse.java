package http.response;

import http.HttpHeaders;
import http.HttpStatus;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";
    private static final byte[] EMPTY = new byte[0];

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

    public HttpResponse contentType(String contentType) {
        return set(CONTENT_TYPE, contentType);
    }

    public void end(byte[] body) throws IOException {
        setContentLength(body.length);
        writeStatusLine();
        writeHeaders();
        writeBody(body);
        writer.flush();
    }

    public void end() throws IOException {
        end(EMPTY);
    }

    private void setContentLength(int length) {
        if (length > 0) {
            headers.set(CONTENT_LENGTH, String.valueOf(length));
        }
    }

    public void sendRedirect(String to) throws IOException {
        status(HttpStatus.FOUND).set(LOCATION, to).end();
    }

    private void writeStatusLine() throws IOException {
        writer.writeBytes(statusLine.build()+ LINE_SEPARATOR);
    }

    private void writeHeaders() throws IOException {
        if (headers.isNotEmpty()) {
            writer.writeBytes(headers.build() + LINE_SEPARATOR);
        }
    }

    private void writeEmptyLine() throws IOException {
        writer.writeBytes(LINE_SEPARATOR);
    }

    private void writeBody(byte[] body) throws IOException {
        if (body.length > 0) {
            writeEmptyLine();
            writer.write(body);
        }
    }
}
