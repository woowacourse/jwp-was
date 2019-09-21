package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseWriter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);

    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-type";
    private static final String LINE_DELIMITER = "\r\n";

    private final DataOutputStream dos;

    public ResponseWriter(DataOutputStream dos) {
        this.dos = dos;
    }

    public void write(HttpResponse response) {
        try {
            writeHeader(dos, response);
            dos.writeBytes(LINE_DELIMITER); // Boundary between http headers and message body
            writeBody(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeader(DataOutputStream dos, HttpResponse response) throws IOException {
        dos.writeBytes(String.format("%s %d %s%s", HTTP_VERSION, response.getStatus().getCode(), response.getStatus().getText(), LINE_DELIMITER));
        writeContentType(dos, response);
        response.getHeaderKeys()
            .forEach(k -> writeHeaderLine(dos, k, response.getHeader(k)));
    }

    private void writeContentType(DataOutputStream dos, HttpResponse response) {
        if (response.getContentType() != null) {
            writeHeaderLine(dos, CONTENT_TYPE_HEADER_KEY, response.getContentType().getHeaderValue());
        }
    }

    private void writeHeaderLine(DataOutputStream dos, String headerKey, String headerValue) {
        try {
            dos.writeBytes(String.format("%s: %s%s", headerKey, headerValue, LINE_DELIMITER));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeBody(DataOutputStream dos, HttpResponse response) {
        try {
            writeBodyIfExist(dos, response);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeBodyIfExist(DataOutputStream dos, HttpResponse response) throws IOException {
        if (response.getBody() != null) {
            dos.write(response.getBody(), 0, response.getBody().length);
        }
    }
}
