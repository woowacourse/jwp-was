package webserver;

import controller.Controller;
import controller.ControllerMapper;
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
import java.nio.charset.StandardCharsets;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            HttpRequest httpRequest = HttpRequestFactory.createRequest(br);
            HttpResponse httpResponse = new HttpResponse(out);

            handle(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (ControllerMapper.isApi(httpRequest)) {
            Controller controller = ControllerMapper.map(httpRequest);
            controller.service(httpRequest, httpResponse);
            return;
        }
        forward(httpRequest, httpResponse);
    }

    void forward(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getPath());
        if (body == null) {
            httpResponse.sendRedirect("index.html");
        }
        httpResponse.forward(httpRequest.getPath());
    }
}
