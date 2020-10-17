package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.HttpRequest;
import model.HttpRequestFactory;
import model.RequestURI;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequestFactory()
                    .create(new BufferedReader(new InputStreamReader(in)));

            RequestURI uri = httpRequest.getHeader()
                    .getRequestLine()
                    .getUri();

            httpRequest
                    .getService()
                    .doService(out, uri);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
