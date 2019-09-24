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
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Map<String, Controller> controllers;

    static {
        controllers = new HashMap<>();
        controllers.put("/user/create", CreateUserController.getInstance());
        controllers.put("/", FileController.getInstance());
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

            route(httpRequest, httpResponse);

            writeResponse(dataOutputStream, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void route(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isContainExtension()) {
            controllers.get("/").service(httpRequest, httpResponse);
            return;
        }

        try {
            Optional.ofNullable(controllers.get(httpRequest.getUri()))
                    .orElseThrow(() -> new ControllerNotFoundException(httpRequest.getUri()))
                    .service(httpRequest, httpResponse);
        } catch (ControllerNotFoundException e) {
            httpResponse.send404Error();
        }
    }

    private void writeResponse(DataOutputStream dataOutputStream, HttpResponse httpResponse) throws IOException {
        dataOutputStream.writeBytes(httpResponse.getHttpStatusLine().toString());
        dataOutputStream.writeBytes(httpResponse.getHttpResponseHeader().toString());

        if (httpResponse.getHttpResponseBody() != null) {
            responseBody(dataOutputStream, httpResponse.getHttpResponseBody().getBody());
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
