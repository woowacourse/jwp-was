package webserver;

import db.DataBase;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpRequestFactory;
import http.QueryParams;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequestFactory.makeHttpRequest(in);
            logger.debug(request.toString());

            if (HttpMethod.GET.match(request.getMethod())) {
                DataOutputStream dos = new DataOutputStream(out);

                byte[] body;
                try {
                    body = FileIoUtils.loadFileFromClasspath(DEFAULT_PATH + request.getUrl().getPath());
                } catch (NullPointerException e) {
                    body = FileIoUtils.loadFileFromClasspath(STATIC_PATH + request.getUrl().getPath());
                }
                response200Header(dos, body.length, request);
                responseBody(dos, body);
            }

            if (HttpMethod.POST.match(request.getMethod()) && request.getUrl().getPath().equals("/user/create")) {
                DataOutputStream dos = new DataOutputStream(out);
                createUser(request.getQueryParams());
                response302Header(dos, "/index.html");
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void createUser(QueryParams queryParams) {
        User user = new User(queryParams.getParam("userId"), queryParams.getParam("password"),
                queryParams.getParam("name"), queryParams.getParam("email"));
        DataBase.addUser(user);
        logger.debug(DataBase.findUserById(user.getUserId()).toString());
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, HttpRequest request) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");

            if (request.getHeaders().getHeader("Accept").contains("text/css")) {
                dos.writeBytes("Content-Type: text/css\r\n");
            } else {
                dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            }
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String redirectPath) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: http://localhost:8080/" + redirectPath + "\r\n");
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
