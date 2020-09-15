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
import http.HttpMethod;
import http.RequestBody;
import http.RequestHeaders;
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
            logger.debug(requestLineAndHeader.toString());

            RequestLine requestLine = RequestLine.from(requestLineAndHeader.get(0));
            RequestHeaders requestHeaders = RequestHeaders.from(
                    requestLineAndHeader.subList(1, requestLineAndHeader.size()));

            Uri uri = requestLine.getUri();
            if ("/user/create".equals(uri.getPath()) && requestLine.equalsMethod(HttpMethod.POST)) {
                int contentLength = Integer.parseInt(requestHeaders.getHeader("Content-Length"));
                String body = IOUtils.readBody(bufferedReader, contentLength);
                RequestBody requestBody = RequestBody.from(body);
                String userId = requestBody.getValue("userId");
                String password = requestBody.getValue("password");
                String name = requestBody.getValue("name");
                String email = requestBody.getValue("email");
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
