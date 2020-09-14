package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpMessage;
import webserver.http.HttpUri;
import webserver.http.body.HttpBody;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String NEW_LINE = System.lineSeparator();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                     connection.getPort());

        try (InputStream in = connection.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {

            HttpMessage httpMessage = HttpMessage.from(br);
            HttpBody httpBody = httpMessage.getHttpBody();

            try {
                User newUser = User.from(httpBody);
                DataBase.addUser(newUser);
                logger.debug("New User created! -> {}", newUser);
            } catch (Exception e) {
                logger.debug("This request is not for creating User");
            }

            HttpUri httpUri = httpMessage.getRequestLine().getHttpUri();
            try {
                byte[] fileBytes = httpUri.readFile();
                String contentType = httpUri.getContentType();
                response200Header(dos, contentType, fileBytes.length);
                responseBody(dos, fileBytes);
            } catch (Exception e) {
                String indexUrl = "/index.html";
                response302Header(dos, indexUrl);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK ");
            dos.writeBytes(NEW_LINE);
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8");
            dos.writeBytes(NEW_LINE);
            dos.writeBytes("Content-Length: " + lengthOfBodyContent);
            dos.writeBytes(NEW_LINE);
            dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found ");
            dos.writeBytes(NEW_LINE);
            dos.writeBytes("Location: " + redirectUrl);
            dos.writeBytes(NEW_LINE);
            dos.writeBytes(NEW_LINE);
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
