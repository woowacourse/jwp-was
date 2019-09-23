package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.Request.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseWriter {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATUS = "Status";
    private static final String WRITE_FORMAT = "%s: %s";

    private DataOutputStream dos;

    public ResponseWriter(DataOutputStream dos) {
        this.dos = dos;
    }

    public void send(Response response, byte[] body) {
        try {
            writeResponse(response);
            responseBody(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponse(Response response) throws IOException {
        for (String key : response.getKeySet()) {
            if (key.equals(STATUS)) {
                dos.writeBytes(response.getHeader(key));
            }
            dos.writeBytes(String.format(WRITE_FORMAT, key, response.getHeader(key)));
        }

        dos.writeBytes("\r\n");
    }
}
