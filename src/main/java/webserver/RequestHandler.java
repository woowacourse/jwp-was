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
import http.StaticFiles;
import model.User;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final String TEMPLATES = "./templates";
    private static final String ROOT_HTML = "/index.html";

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(bufferedReader);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            HttpResponse httpResponse = new HttpResponse(dataOutputStream);

            if (httpRequest.isStaticFile()) {
                byte[] body = FileIoUtils.loadFileFromClasspath(
                    StaticFiles.getDirectoryEndsWith(httpRequest.getHttpPath()) + httpRequest.getHttpPath());
                httpResponse.response200Header(httpRequest.getContentType(), body.length);
                httpResponse.responseBody(body);
            } else {
                if (httpRequest.getHttpPath().contains("/user/create")) {
                    User user = new User(
                        httpRequest.getHttpBodyValueOf("userId"),
                        httpRequest.getHttpBodyValueOf("password"),
                        httpRequest.getHttpBodyValueOf("name"),
                        httpRequest.getHttpBodyValueOf("email")
                    );
                    DataBase.addUser(user);
                    logger.debug("Saved User: {}", DataBase.findUserById(httpRequest.getHttpBodyValueOf("userId")));
                }

                byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATES + ROOT_HTML);
                httpResponse.response302Header(ROOT_HTML);
                httpResponse.responseBody(body);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
