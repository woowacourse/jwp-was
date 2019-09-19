package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private DataOutputStream dos;
    private String classPath;

    public Response(DataOutputStream dos, String classPath) {
        this.dos = dos;
        this.classPath = classPath;
    }

    public void response200(String type) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(classPath);

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + type + ";charset=utf-8\r\n");
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
