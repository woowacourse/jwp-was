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
import http.HttpRequest;
import http.HttpResponse;
import http.HttpBody;
import http.HttpStatus;
import http.SimpleHttpRequest;
import model.User;
import http.ContentType;
import utils.FileIoUtils;
import utils.StaticResourceMatcher;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (
            InputStream inputStream = connection.getInputStream();
            OutputStream outputStream = connection.getOutputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        ) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest httpRequest = SimpleHttpRequest.of(bufferedReader);
            logger.debug(System.lineSeparator() + httpRequest.toString());
            DataOutputStream dos = new DataOutputStream(outputStream);
            HttpResponse httpResponse = new HttpResponse();
            // dispatcher.service(httpRequest, httpResponse);
            if (StaticResourceMatcher.isStaticResourcePath(httpRequest.getURI())) {
                byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getURI());
                ContentType contentType = ContentType.findByURI(httpRequest.getURI());
                httpResponse.setBody(body, contentType);
            } else if (httpRequest.getURI().contains("/user/create")) {
                HttpBody httpBody = httpRequest.getBody();
                User user = new User(
                    httpBody.get("userId"),
                    httpBody.get("password"),
                    httpBody.get("name"),
                    httpBody.get("email"));
                DataBase.addUser(user);
                httpResponse.setStatus(HttpStatus.FOUND);
                httpResponse.addHeader("Location", "/index.html");
            }
            httpResponse.send(dos);
            logger.debug(System.lineSeparator() + httpResponse.toString());
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
