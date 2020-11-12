package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class HttpResponse {
    private static final String DELIMITER = ": ";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";
    private static final String CHARSET_UTF_8 = ";charset=utf-8";

    private final DataOutputStream dos;
    private final HttpStatusLine httpStatusLine;
    private final HttpHeaders httpHeaders;

    public HttpResponse(OutputStream out, HttpStatus httpStatus, String version) {
        this.dos = new DataOutputStream(out);
        this.httpStatusLine = HttpStatusLine.of(httpStatus, version);
        this.httpHeaders = new HttpHeaders(new HashMap<>());
    }

    public void response200Header(int lengthOfBodyContent, String contentType) throws IOException {
        addResponseHeader(CONTENT_TYPE, contentType + CHARSET_UTF_8);
        addResponseHeader(CONTENT_LENGTH, String.valueOf(lengthOfBodyContent));
        writeStatusLine();
        writeHeaders();
    }

    public void response302Header(int lengthOfBodyContent, String contentType, String url) throws IOException {
        addResponseHeader(CONTENT_TYPE, contentType + CHARSET_UTF_8);
        addResponseHeader(CONTENT_LENGTH, String.valueOf(lengthOfBodyContent));
        addResponseHeader(LOCATION, url);
        writeStatusLine();
        writeHeaders();
    }

    public void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    private void addResponseHeader(String headerType, String value) {
        httpHeaders.addHeader(headerType, value);
    }

    private void writeStatusLine() throws IOException {
        dos.writeBytes(httpStatusLine.getLine() + System.lineSeparator());
    }

    private void writeHeaders() throws IOException {
        for (String headerType : httpHeaders.keySet()) {
            String header = headerType + DELIMITER + httpHeaders.get(headerType) + System.lineSeparator();
            dos.writeBytes(header);
        }
        dos.writeBytes(System.lineSeparator());
    }
}
