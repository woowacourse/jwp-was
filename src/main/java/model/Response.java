package model;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATUS = "Status";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";
    private static final String HTTP_1_1 = "HTTP/1.1";

    private DataOutputStream dos;
    private Map<String, String> header;

    public Response(DataOutputStream dos) {
        this.dos = dos;
        header = new LinkedHashMap<>();
    }

    public void responseResource(String classPath) throws URISyntaxException, IOException {
        Path path = Paths.get(Objects.requireNonNull(FileIoUtils.class.getClassLoader().getResource(classPath)).toURI());
        File file = new File(path.toString());
        String mimeType = new Tika().detect(file);

        byte[] body = FileIoUtils.loadFileFromClasspath(classPath);

        header.put(STATUS, HTTP_1_1 + " 200 OK \r\n");
        header.put(CONTENT_TYPE, mimeType + ";charset=utf-8\r\n");
        header.put(CONTENT_LENGTH, body.length + "\r\n");

        response200(body);
    }

    public void response300(String location) {
        try {
            header.put(STATUS, HTTP_1_1 + " 302 Found \r\n");
            header.put(LOCATION, location + "\r\n");
            sendRedirect();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response405() {
        try {
            header.put(STATUS, HTTP_1_1 + "405 Method Not Allowed \r\n");
            sendRedirect();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200(byte[] body) {
        try {
            forward(body);
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
}
