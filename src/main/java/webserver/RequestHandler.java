package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import utils.StringUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String RESOURCE_PATH = "./templates";
    private static final String STATIC_PATH = "./static";
    private static final String HTML_CONTENT_TYPE = "text/html";

    private Socket connection;
    private HttpRequest httpRequest;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            httpRequest = HttpRequest.from(new BufferedReader(new InputStreamReader(in)));
            DataOutputStream dos = new DataOutputStream(out);

            byte[] body = null;
            if (httpRequest.getPath().contains("/user/create")) {
                DataBase.addUser(User.from(StringUtils.readParameters(httpRequest.getBody())));
                response302Header(dos, "/index.html");
            } else {
                if (httpRequest.getHttpHeaders().get("Accept").contains(HTML_CONTENT_TYPE)) {
                    body = FileIoUtils.loadFileFromClasspath(
                            RESOURCE_PATH + httpRequest.getPath());
                    response200Header(dos, "text/html;charset=utf-8", body.length);
                } else {
                    body = FileIoUtils.loadFileFromClasspath(
                            STATIC_PATH + httpRequest.getPath());
                    response200Header(dos, "text/css", body.length);
                }
            }
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, String contentType,
            int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
