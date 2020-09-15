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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import http.HttpRequest;
import http.RequestLine;
import http.Uri;
import model.User;
import utils.FileIoUtils;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
             OutputStream outputStream = connection.getOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {

            List<String> requestLineAndHeader = IOUtils.readHeader(bufferedReader);
            HttpRequest httpRequest = HttpRequest.from(requestLineAndHeader.get(0),
                    requestLineAndHeader.subList(1, requestLineAndHeader.size()));
            logger.debug(requestLineAndHeader.toString());

            RequestLine requestLine = httpRequest.getRequestLine();
            Uri uri = requestLine.getUri();
            if ("/user/create".equals(uri.getPath())) {
                String userId = uri.getParameter("userId");
                String password = uri.getParameter("password");
                String name = uri.getParameter("name");
                String email = uri.getParameter("email");
                User user = new User(userId, password, name, email);
                DataBase.addUser(user);
            } else if (uri.getPath().endsWith(".html")) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + requestLine.getPath());
                response200Header(dataOutputStream, body.length);
                responseBody(dataOutputStream, body);
            }
            byte[] body = "hello, world".getBytes();
            response200Header(dataOutputStream, body.length);
            responseBody(dataOutputStream, body);
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
