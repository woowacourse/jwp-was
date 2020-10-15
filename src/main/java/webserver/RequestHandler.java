package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import web.HttpMethod;
import web.HttpRequest;
import web.StaticFile;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            HttpRequest httpRequest = HttpRequest.from(br);

            String path = httpRequest.getPath();
            byte[] body;
            if (HttpMethod.POST == httpRequest.getMethod()) {
                if (path.equals("/user/create")) {
                    User user = User.from(httpRequest.getRequestBody());
                    DataBase.addUser(user);
                    body = user.toString().getBytes();
                    DataOutputStream dos = new DataOutputStream(out);
                    response302Header(dos, "/index.html");
                    responseBody(dos, body);
                }
            } else if (HttpMethod.GET == httpRequest.getMethod()) {
                DataOutputStream dos = new DataOutputStream(out);
                StaticFile staticFile = StaticFile.of(path);
                body = FileIoUtils.loadFileFromClasspath(staticFile.getPrefix() + path);
                response200Header(dos, body.length, staticFile.getType());
                responseBody(dos, body);
            } else if (HttpMethod.NONE == httpRequest.getMethod()) {
                DataOutputStream dos = new DataOutputStream(out);
                response405Header(dos);
            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response405Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 405 Method Not Allowed" + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response404Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found" + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found" + System.lineSeparator());
            dos.writeBytes("Location: " + redirectUrl + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK" + System.lineSeparator());
            dos.writeBytes("Content-Type: " + contentType + System.lineSeparator());
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
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
