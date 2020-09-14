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
import web.RequestBody;
import web.RequestHeader;
import web.RequestLine;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            RequestLine requestLine = new RequestLine(bufferedReader);
            RequestHeader requestHeader = new RequestHeader(bufferedReader);
            RequestBody requestBody = new RequestBody(bufferedReader, requestHeader.getContentLength());

            DataOutputStream dos = new DataOutputStream(out);
            logger.debug("requestLine.getPath {}", requestLine.getPath());
            String requestPath = requestLine.getPath();
            byte[] body;
            if (requestPath.endsWith("/user/create")) {
                String userId = requestLine.getParam("userId");
                String password = requestLine.getParam("password");
                String name = requestLine.getParam("name");
                String email = requestLine.getParam("email");

                User user = new User(userId, password, name, email);
                DataBase.addUser(user);
                body = user.toString().getBytes();
            } else {
                body = FileIoUtils.loadFileFromClasspath("./templates" + requestPath);
            }
            logger.debug("body {}", body);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
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
