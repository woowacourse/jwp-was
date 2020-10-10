package http;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final DataOutputStream dataOutputStream;
    private final HttpHeaders httpHeaders;

    public HttpResponse(final DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
        this.httpHeaders = HttpHeaders.empty();
    }

    public void response200Header(String contentType, int lengthOfBodyContent) {
        httpHeaders.setContentType(contentType);
        httpHeaders.setContentLength(lengthOfBodyContent);
        responseHeader(ResponseStatusLine.OK);
    }

    public void sendRedirect(String location) {
        httpHeaders.setLocation(location);
        responseHeader(ResponseStatusLine.FOUND);
        noContent();
    }

    public void ok(byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void noContent() {
        try {
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseHeader(final ResponseStatusLine responseStatusLine) {
        try {
            responseHeader(responseStatusLine, httpHeaders.isNotEmpty());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(final ResponseStatusLine responseStatusLine, final boolean hasHeader) throws
            IOException {
        dataOutputStream.writeBytes(responseStatusLine.toMessage() + LINE_SEPARATOR);
        if (hasHeader) {
            dataOutputStream.writeBytes(httpHeaders.toMessage() + LINE_SEPARATOR);
        }
        dataOutputStream.writeBytes(LINE_SEPARATOR);
    }
}
