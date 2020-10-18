package webserver;

import controller.Controller;
import controller.ControllerMapper;
import http.factory.HttpRequestFactory;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.servlet.SessionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final ControllerMapper controllerMapper;

    public RequestHandler(Socket connectionSocket, ControllerMapper mapper) {
        this.connection = connectionSocket;
        this.controllerMapper = mapper;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            HttpRequest httpRequest = HttpRequestFactory.createRequest(br);
            HttpResponse httpResponse = new HttpResponse(out);

            httpRequest.sessionCheck(httpResponse, SessionContainer.getInstance());

            handle(httpRequest, httpResponse);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (controllerMapper.isApi(httpRequest)) {
            Controller controller = controllerMapper.map(httpRequest);
            controller.service(httpRequest, httpResponse);
            return;
        }
        findStaticResources(httpRequest, httpResponse);
    }

    private void findStaticResources(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (httpRequest.isNotFound()) {
            httpResponse.notFound();
            return;
        }
        httpResponse.forward(httpRequest);
    }
}
