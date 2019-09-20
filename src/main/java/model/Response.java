package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;

import javax.activation.MimetypesFileTypeMap;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.Tika;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private DataOutputStream dos;
    private String classPath;

    public Response(DataOutputStream dos, String classPath) {
        this.dos = dos;
        this.classPath = classPath;
    }

    public void response200() {
        try {
            Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(classPath).toURI());
            File file = new File(path.toString());

            String mimeType = new Tika().detect(file);

            byte[] body = FileIoUtils.loadFileFromClasspath(classPath);

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + mimeType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");

            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void response300(String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
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
}
