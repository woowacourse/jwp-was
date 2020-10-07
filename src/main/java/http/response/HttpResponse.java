package http.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String BLANK = " ";
    private static final String COLON = ": ";
    private static final String CONTENT_TYPE_TEXT_HTML = "text/html";
    private static final String RESPONSE_HEADER_LOCATION = "Location";
    private static final String RESPONSE_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String RESPONSE_HEADER_CONTENT_LENGTH = "Content-Length";
    private static final String REDIRECT_HOME = "http://localhost:8080/index.html";
    private static final String CHARSET_UTF_8 = ";charset=utf-8";

    private final DataOutputStream dos;
    private final HttpResponseLine httpResponseLine;
    private final HttpResponseHeader httpResponseHeader;
    private final HttpResponseBody httpResponseBody;

    public HttpResponse(final DataOutputStream dos) {
        this.dos = dos;
        this.httpResponseLine = new HttpResponseLine();
        this.httpResponseHeader = new HttpResponseHeader();
        this.httpResponseBody = new HttpResponseBody();
    }

    public void response200(final String contentType, final byte[] body) throws IOException {
        httpResponseLine.setHttpStatus(HttpStatus.OK);
        initResponseHeader(contentType, body.length);
        httpResponseBody.setBody(body);
        render();
    }

    public void response302(int lengthOfBodyContent) throws IOException {
        httpResponseLine.setHttpStatus(HttpStatus.FOUND);
        initResponseHeader(CONTENT_TYPE_TEXT_HTML, lengthOfBodyContent);
        httpResponseHeader.addResponseHeader(RESPONSE_HEADER_LOCATION, REDIRECT_HOME);
        render();
    }

    private void initResponseHeader(String contentType, int lengthOfBodyContent) {
        httpResponseHeader.addResponseHeader(RESPONSE_HEADER_CONTENT_TYPE, contentType + CHARSET_UTF_8);
        httpResponseHeader.addResponseHeader(RESPONSE_HEADER_CONTENT_LENGTH, lengthOfBodyContent);
    }

    private void render() throws IOException {
        responseLine();
        responseHeader();
        responseBody();
        this.dos.flush();
    }

    private void responseLine() throws IOException {
        final HttpStatus httpStatus = httpResponseLine.getHttpStatus();
        this.dos.writeBytes(httpResponseLine.getVersion() + BLANK + httpStatus.getCode() + BLANK + httpStatus + NEW_LINE);
    }

    private void responseHeader() throws IOException {
        for (String name : httpResponseHeader.keySet()) {
            this.dos.writeBytes(name + COLON + httpResponseHeader.getValue(name) + NEW_LINE);
        }
    }

    private void responseBody() throws IOException {
        final byte[] body = httpResponseBody.getBody();
        this.dos.writeBytes(NEW_LINE);
        this.dos.write(body, 0, body.length);
    }
}
