package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATUS = "Status";

    private DataOutputStream dos;
    private Map<String, String> header;

    public Response(DataOutputStream dos) {
        this.dos = dos;
        header = new LinkedHashMap<>();
    }

    public void response200(byte[] body) {
        try {
            forward(body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response300(String location) {
        try {
            header.put(STATUS, "HTTP/1.1 302 Found \r\n");
            header.put("Location", location + "\r\n");
            sendRedirect();
        } catch (IOException e) {
            logger.error(e.getMessage());
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

    private void forward(byte[] body) throws IOException {
        writeResponse();
        responseBody(body);
    }

    private void sendRedirect() throws IOException {
        writeResponse();
        dos.flush();
    }

    private void writeResponse() throws IOException {
        for (String key : header.keySet()) {
            if (key.equals(STATUS)) {
                dos.writeBytes(header.get(key));
            }
            dos.writeBytes(key + ": " + header.get(key));
        }

        dos.writeBytes("\r\n");
    }

    public void setHeader(String key, String value) {
        header.put(key, value);
    }
}
