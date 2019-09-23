package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseWriter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);

    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-type";
    private static final String SET_COOKIE_HEADER_KEY = "Set-Cookie";
    private static final String LINE_DELIMITER = "\r\n";

    private final DataOutputStream dos;

    public ResponseWriter(DataOutputStream dos) {
        this.dos = dos;
    }

    public void write(HttpRequest request, HttpResponse response) {
        try {
            writeHeader(response);
            writeCookie(request, response);
            dos.writeBytes(LINE_DELIMITER); // Boundary between http headers and message body
            writeBody(response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeader(HttpResponse response) throws IOException {
        dos.writeBytes(String.format("%s %d %s%s", HTTP_VERSION, response.getStatus().getCode(), response.getStatus().getText(), LINE_DELIMITER));
        writeContentType(response);
        response.getHeaderKeys()
            .forEach(k -> writeHeaderLine(k, response.getHeader(k)));
    }

    private void writeContentType(HttpResponse response) {
        if (response.getContentType() != null) {
            writeHeaderLine(CONTENT_TYPE_HEADER_KEY, response.getContentType().getHeaderValue());
        }
    }

    private void writeHeaderLine(String headerKey, String headerValue) {
        try {
            dos.writeBytes(String.format("%s: %s%s", headerKey, headerValue, LINE_DELIMITER));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeCookie(HttpRequest request, HttpResponse response) {
        if (request.hasValidSession()) {
            writeHeaderLine(SET_COOKIE_HEADER_KEY, String.format("%s=%s; path=/", "sid", request.getSession().getId()));
        }
        response.getCookieKeys()
            .forEach(k -> writeHeaderLine(SET_COOKIE_HEADER_KEY, String.format("%s=%s; path=/", k, response.getCookie(k))));
    }

    private void writeBody(HttpResponse response) {
        try {
            writeBodyIfExist(response);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeBodyIfExist(HttpResponse response) throws IOException {
        if (response.getBody() != null) {
            dos.write(response.getBody(), 0, response.getBody().length);
        }
    }
}
