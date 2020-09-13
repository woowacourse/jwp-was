package http;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final String HEADER_VALUE_SEPARATOR = "; ";
    private static final String ENCODING_CHARSET_UTF_8 = "charset=UTF-8";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dataOutputStream;

    public HttpResponse(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void response200Header(String contentType, int lengthOfBodyContent) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK" + LINE_SEPARATOR);
            dataOutputStream.writeBytes(
                "Content-Type: " + contentType + HEADER_VALUE_SEPARATOR + ENCODING_CHARSET_UTF_8 + LINE_SEPARATOR);
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + LINE_SEPARATOR);
            dataOutputStream.writeBytes(LINE_SEPARATOR);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302Header(String url) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 302 FOUND" + LINE_SEPARATOR);
            dataOutputStream.writeBytes("Location: " + url + LINE_SEPARATOR);
            dataOutputStream.writeBytes(LINE_SEPARATOR);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
