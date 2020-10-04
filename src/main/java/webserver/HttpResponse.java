package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;

public class HttpResponse {
    private static final int OFFSET = 0;

    private HttpStatus httpStatus;
    private final DataOutputStream dos;
    private final Map<String, String> header;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.header = new TreeMap<>();
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public void forward(byte[] body) throws IOException {
        writeStatus();
        for (Map.Entry<String, String> entry : header.entrySet()) {
            writeWithLineSeparator(String.format("%s: %s", entry.getKey(), entry.getValue()));
        }
        dos.writeBytes(System.lineSeparator());
        dos.write(body, OFFSET, body.length);
        dos.flush();
    }

    public void sendRedirect(String path) throws IOException {
        writeStatus();
        writeWithLineSeparator(String.format("Location: http://localhost:8080%s", path));
        writeWithLineSeparator("Content-Type: text/html;charset=utf-8");
        for (Map.Entry<String, String> entry : header.entrySet()) {
            writeWithLineSeparator(entry.getKey() + ": " + entry.getValue());
        }
        dos.writeBytes(System.lineSeparator());
        dos.flush();
    }

    private void writeStatus() throws IOException {
        writeWithLineSeparator(String.format("HTTP/1.1 %d %s", httpStatus.getNumber(), httpStatus.name()));
    }

    private void writeWithLineSeparator(String contents) throws IOException {
        dos.writeBytes(String.format("%s%s", contents, System.lineSeparator()));
    }

    public Map<String, String> getHeader() {
        return header;
    }
}
