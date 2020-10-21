package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String ENCODING = "UTF-8";

    private final HandlerMapping handlerMapping = new HandlerMapping();
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, ENCODING));
            HttpResponse httpResponse = new HttpResponse(out);
            handle(new HttpRequest(bufferedReader), httpResponse);
            httpResponse.send();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        handlerMapping.mapping(httpRequest, httpResponse);
    }
}