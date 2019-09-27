package dev.luffy.webserver;

import dev.luffy.http.request.HttpRequest;
import dev.luffy.http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {

            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            ResponseHandler.run(request, response);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
