package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(bufferedReader);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            HttpResponse httpResponse = new HttpResponse(dataOutputStream);

            ResponseHandler.run(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
