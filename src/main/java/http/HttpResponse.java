package http;

import http.exception.EmptyStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;
    private HttpStatus status;
    private HttpHeader header = new HttpHeader();
    private byte[] body;

    public HttpResponse(DataOutputStream dataOutputStream) {
        this.dos = dataOutputStream;
    }

    public void setStatus(int statusCode) {
        status = HttpStatus.of(statusCode);
    }

    public void addHeader(String key, String value) {
        header.addHeader(key, value);
    }

    public void setBody(byte[] body) {
        this.body = body;
        header.addHeader("Content-Length", String.valueOf(body.length));
    }

    public void send() {
        if (status == null) {
            throw new EmptyStatusException();
        }
        try {
            dos.writeBytes("HTTP/1.1 " + status.getStatusCode() + " " + status.getStatus() + "\r\n");
            writeHeader();
            if (body != null) {
                dos.write(body, 0, body.length);
            }
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void writeHeader() throws IOException {
        for (Map.Entry<String, String> header : header.getHeaders()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
    }
}
