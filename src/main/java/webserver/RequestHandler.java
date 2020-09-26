package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Model;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(br);
            printHeader(httpRequest);
            createModel(httpRequest);
            byte[] body = FileIoUtils.findStaticFile(httpRequest.getPath());
            ResponseFactory.response(out, httpRequest.getPath(), body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void printHeader(HttpRequest httpRequest) {
        logger.debug("header : {}", httpRequest.getMethodName() + " " + httpRequest.getPath());
        httpRequest.getHeader()
            .forEach((key, value) -> logger.debug("header : {}", String.format("%s: %s", key, value)));
    }

    private void createModel(HttpRequest httpRequest) {
        Model model = httpRequest.createModel();
        if (model != null) {
            printParameter(httpRequest);
            logger.debug("model : {}", model.toString());
        }
    }

    private void printParameter(HttpRequest httpRequest) {
        httpRequest.getParameter()
            .forEach((key, value) -> logger.debug("body : {}", String.format("%s = %s", key, value)));
    }
}
