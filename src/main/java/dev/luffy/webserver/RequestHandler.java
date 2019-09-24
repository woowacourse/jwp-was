package dev.luffy.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import dev.luffy.http.MimeType;
import dev.luffy.http.request.HttpRequest;
import dev.luffy.model.User;
import dev.luffy.utils.FileIoUtils;
import dev.luffy.utils.HttpRequestUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);

            logger.debug("HttpRequest : {}", httpRequest);

            DataOutputStream dos = new DataOutputStream(out);

            staticFileResource(httpRequest, dos);

//            HttpResponse httpResponse = RequestMapper.getController()

            createUser(httpRequest, dos);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void createUser(HttpRequest httpRequest, DataOutputStream dos) {

        if (httpRequest.isPathEquals("/user/create") && httpRequest.isPost()) {
            User user = new User(
                    httpRequest.getBodyParameter("userId"),
                    httpRequest.getBodyParameter("password"),
                    httpRequest.getBodyParameter("name"),
                    httpRequest.getBodyParameter("email")
            );

            logger.debug("Generated User : {}", user);

            response302Header(dos, "/index.html");
        }
    }

    private void staticFileResource(HttpRequest httpRequest, DataOutputStream dos) throws IOException, URISyntaxException {
        if (httpRequest.pathHasExtension()) {
            MimeType mimeType = MimeType.of(httpRequest.pathExtension());
            String filePath = HttpRequestUtils.filePathBuilder(httpRequest.getPath(), mimeType);

            byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
            response200Header(dos, body.length, mimeType);
            responseBody(dos, body);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, MimeType mimeType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + mimeType.getMimeType() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
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

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
