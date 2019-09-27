package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATUS = "Status";
    private static final String WRITE_FORMAT = "%s: %s";
    private static final String NEXT_LINE = "\r\n";
    private static final String HTTP_1_1 = "HTTP/1.1";
    private static final String STATUS_FORMAT = "%s %d %s %s";
    private static final String NEST_LINE = "\r\n";

    public ResponseWriter() {
    }

    public void send(OutputStream out, Response response) {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            responseStatus(dos, response);
            writeResponse(dos, response);
            responseBody(dos, response);
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
    }

    private void responseStatus(DataOutputStream dos, Response response) throws IOException {
        int statusCode = response.getHttpStatus().getStatusCode();
        String message = response.getHttpStatus().getMessage();
        dos.writeBytes(String.format(STATUS_FORMAT, HTTP_1_1, statusCode, message, NEST_LINE));
    }

    private void responseBody(DataOutputStream dos, Response response) throws IOException {
        if (response.getBody() != null) {
            byte[] body = response.getBody();
            dos.write(body, 0, body.length);
            dos.write(body, 0, body.length);
        }

        dos.flush();
    }

    private void writeResponse(DataOutputStream dos, Response response) throws IOException {
        for (String key : response.getKeySet()) {
            writeHeader(dos, response, key);
        }

        dos.writeBytes(NEXT_LINE);
    }

    private void writeHeader(DataOutputStream dos, Response response, String key) throws IOException {
        if (key.equals(STATUS)) {
            dos.writeBytes(response.getHeader(key));
        }
        dos.writeBytes(String.format(WRITE_FORMAT, key, response.getHeader(key)));
    }
}
