package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {
    private static final String NEW_LINE = "\r\n";
    private final String HEADER_DELIMITER = ": ";
    private final HttpVersion httpVersion;
    private final String LINE_DELIMITER = " ";
    private DataOutputStream dos;
    private Map<String, Object> headers;

    public HttpResponse(DataOutputStream dos, HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
        this.dos = dos;
    }

    public void ok() throws IOException {
        writeLine(HttpStatus.OK);
    }

    public void redirect(String location) throws IOException {
        writeLine(HttpStatus.FOUND);
        appendHeader("Location", location);
    }

    public void appendHeader(String key, Object value) {
        headers.put(key, value);
    }

    public void writeLine(HttpStatus httpStatus) throws IOException {
        dos.writeBytes(httpVersion.name() + LINE_DELIMITER + httpStatus.getCode() + LINE_DELIMITER + httpStatus.getName());
    }

    public void writeHeader() throws IOException {
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            dos.writeBytes(entry.getKey() + HEADER_DELIMITER + entry.getValue());
        }
    }

    public void writeBody(byte[] body) throws IOException {
        dos.write(body);
    }
}


