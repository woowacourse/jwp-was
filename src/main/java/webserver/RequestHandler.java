package webserver;

import http.controller.ControllerHandler;
import http.controller.NotFoundException;
import http.model.HttpProtocols;
import http.model.HttpRequest;
import http.model.HttpResponse;
import http.model.HttpStatus;
import http.supoort.HttpRequestParser;
import http.view.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private ControllerHandler controllerHandler;

    public RequestHandler(Socket connectionSocket, ControllerHandler controllerHandler) {
        this.connection = connectionSocket;
        this.controllerHandler = controllerHandler;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            handleRequest(in, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleRequest(InputStream in, OutputStream out) {
        try {
            HttpRequest httpRequest = HttpRequestParser.parse(in);
            HttpResponse httpResponse = controllerHandler.doService(httpRequest);
            Renderer.render(httpResponse, new DataOutputStream(out));
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            error404(out);
        } catch (Exception e) {
            logger.error(e.getMessage());
            error500(out);
        }
    }

    private void error404(OutputStream out) {
        HttpResponse httpResponse = new HttpResponse.Builder()
                .forward("./templates/404_ERROR.html")
                .status(HttpStatus.NOT_FOUND)
                .build();
        try {
            Renderer.render(httpResponse, new DataOutputStream(out));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void error500(OutputStream out) {
        HttpResponse httpResponse = new HttpResponse.Builder()
                .protocols(HttpProtocols.HTTP1_1)
                .status(HttpStatus.SERVER_ERROR)
                .build();
        try {
            Renderer.render(httpResponse, new DataOutputStream(out));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
