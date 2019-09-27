package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final String NEW_LINE = "\r\n";
    private final String HEADER_DELIMITER = ": ";
    private final String LINE_DELIMITER = " ";
    private HttpVersion httpVersion;
    private DataOutputStream dos;
    private HttpStatus httpStatus;
    private Map<String, Object> headers = new HashMap<>();

    public HttpResponse(DataOutputStream dos, HttpVersion httpVersion) {
        this.dos = dos;
        this.httpVersion = httpVersion;
    }

    public void ok() {
        httpStatus = HttpStatus.OK;
    }

    public void redirect(String location) {
        httpStatus = HttpStatus.FOUND;
        appendHeader("Location", location);
    }

    public void error(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void appendHeader(String key, Object value) {
        headers.put(key, value);
    }

    public void appendContentType(FileType fileType) {
        appendHeader("CONTENT_TYPE", fileType.getMemeName());
    }

    public void writeLine() throws IOException {
        dos.writeBytes(httpVersion.name() + LINE_DELIMITER + httpStatus.getCode() + LINE_DELIMITER + httpStatus.getName() + NEW_LINE);
    }

    public void writeHeader() throws IOException {
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            dos.writeBytes(entry.getKey() + HEADER_DELIMITER + entry.getValue() + NEW_LINE);
        }
    }

    public void writeBody(byte[] body) throws IOException {
        dos.writeBytes(NEW_LINE);
        dos.write(body, 0, body.length);
    }

    public void end() throws IOException {
        dos.flush();
    }
}


