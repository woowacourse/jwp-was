package webserver;

import db.DataBase;
import http.common.HttpMethod;
import http.request.HttpRequest;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

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

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(br);
            if (httpRequest.getPath().equals("/user/create") && httpRequest.getMethod().equals(HttpMethod.POST)) {
                User user = new User(httpRequest.getEntityValue("userId"),
                        httpRequest.getEntityValue("password"),
                        httpRequest.getEntityValue("name"),
                        httpRequest.getEntityValue("email"));
                DataBase.addUser(user);
                logger.debug("user : {}", user);
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = new byte[1];

            try {
                body = FileIoUtils.loadFileFromClasspath("./templates" + httpRequest.getPath());
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
            response200Header(dos, body.length);
            responseBody(dos, body);
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
