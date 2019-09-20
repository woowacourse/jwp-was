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

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATUS = "Status";

    private DataOutputStream dos;
    private String classPath;
    private Map<String, String> header;

    public Response(DataOutputStream dos, String classPath) {
        this.dos = dos;
        this.classPath = classPath;
        header = new LinkedHashMap<>();
    }

    public void response200() {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(classPath).toURI());
            File file = new File(path.toString());

            String mimeType = new Tika().detect(file);

            byte[] body = FileIoUtils.loadFileFromClasspath(classPath);

            header.put(STATUS, "HTTP/1.1 200 OK \r\n");
            header.put("Content-Type", mimeType + ";charset=utf-8\r\n");
            header.put("Content-Length", body.length + "\r\n");

            forward(body);
        } catch (IOException | URISyntaxException e) {
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
}
