package webserver;

import controller.Controller;
import controller.CreateUserController;
import controller.FileController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RequestParser;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Map<String, Controller> controllers;

    static {
        controllers = new HashMap<>();
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/", new FileController());
    }

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            HttpRequest httpRequest = new HttpRequest(RequestParser.parse(inputStream));
            HttpResponse httpResponse = new HttpResponse();

            logger.debug("RequestLine: {}", httpRequest.getHttpRequestLine().toString());

            if (httpRequest.isContainExtension()) {
                controllers.get("/").service(httpRequest, httpResponse);
            } else {
                controllers.get(httpRequest.getUri()).service(httpRequest, httpResponse);
            }

            dataOutputStream.writeBytes(httpResponse.getHttpStatusLine().toString());
            dataOutputStream.writeBytes(httpResponse.getHttpResponseHeader().toString());

            responseBody(dataOutputStream, httpResponse.getHttpResponseBody().getBody());

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dataOutputStream, byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
