package webserver;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponse;
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

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequestFactory.makeHttpRequest(in);
            logger.debug(request.toString());

            HttpResponse response = new HttpResponse(request.getVersion(), out);
            ServletHandler.handle(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
