package webserver;

import controller.Controller;
import controller.ControllerMapper;
import controller.UserController;
import http.factory.HttpRequestFactory;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

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

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        initControllerMapper();
    }

    private void initControllerMapper() {
        ControllerMapper mapper = ControllerMapper.getInstance();
        if (mapper.isEmpty()) {
            mapper.addController(new UserController());
        }
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            HttpRequest httpRequest = HttpRequestFactory.createRequest(br);
            HttpResponse httpResponse = new HttpResponse(out);

            handle(httpRequest, httpResponse);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (ControllerMapper.getInstance().isApi(httpRequest)) {
            Controller controller = ControllerMapper.getInstance().map(httpRequest);
            controller.service(httpRequest, httpResponse);
            return;
        }
        findStaticResources(httpRequest, httpResponse);
    }

    private void findStaticResources(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (isNotFound(httpRequest)) {
            httpResponse.notFound();
            return;
        }
        httpResponse.forward(httpRequest);
    }

    private boolean isNotFound(HttpRequest httpRequest) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getPath());
        return body.length == 0;
    }
}
