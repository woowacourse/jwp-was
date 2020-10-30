package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import controller.ControllerMapper;
import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.response.HttpResponse;

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
            final BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));

            final HttpRequest httpRequest = HttpRequestParser.parse(br);
            final HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out), httpRequest);

            final Controller controller = ControllerMapper.find(httpRequest.getUrl());

            controller.service(httpRequest, httpResponse);
        } catch (IOException | URISyntaxException | CloneNotSupportedException e) {
            logger.error(e.getMessage());
        }
    }
}
