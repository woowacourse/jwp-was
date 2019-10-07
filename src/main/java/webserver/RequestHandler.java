package webserver;

import http.application.Controller;
import http.application.ControllerMapper;
import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.request.Url;
import http.response.HttpResponse;
import http.response.HttpResponseSender;
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

    RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestParser.parse(in);
            DataOutputStream dos = new DataOutputStream(out);

            Url requestUrl = httpRequest.getUrl();
            logger.info("request url: {}", requestUrl);

            Controller controller = ControllerMapper.controllerMapping(requestUrl.getUrl());
            HttpResponse httpResponse = new HttpResponse();
            controller.service(httpRequest, httpResponse);
            httpResponse.addJSessionId(httpRequest.getJSessionId());

            HttpResponseSender.send(dos, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
