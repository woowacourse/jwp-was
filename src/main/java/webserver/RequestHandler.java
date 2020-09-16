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
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class RequestHandler implements Runnable {
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
                httpResponse.responseOk(httpRequest);
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

                httpResponse.responseFound();
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
