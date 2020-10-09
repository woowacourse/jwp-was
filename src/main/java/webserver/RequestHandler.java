package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.HttpRequest;
import http.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final FrontController frontController = new FrontController();

    private final Socket connection;

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
            HttpRequest httpRequest = HttpRequest.from(bufferedReader);
            logger.info("Request: {}", httpRequest.getRequestLine());
            HttpResponse httpResponse = new HttpResponse(dataOutputStream);
            frontController.service(httpRequest, httpResponse);
        } catch (Exception exception) {
            logger.error("Unhandled exception occur. ", exception);
        }
    }
}
