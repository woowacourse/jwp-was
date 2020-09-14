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

            if (httpBody.isNotEmpty()) {
                User newUser = User.from(httpBody);
                DataBase.addUser(newUser);
                logger.debug("New User Created! : {}", newUser);
            }

            HttpUri httpUri = httpMessage.getRequestLine().getHttpUri();
            byte[] fileBytes = httpUri.readFile();
            response200Header(dos, fileBytes.length);
            responseBody(dos, fileBytes);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
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
}
