package webserver;

import http.request.Request;
import http.response.Response;
import http.response.ResponseHeader;
import http.response.StatusLine;
import http.utils.RequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.support.ControllerMapper;
import webserver.support.ResponseWriter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

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
            Request request = RequestFactory.makeRequest(in);
            Response response = new Response(new StatusLine(request.extractHttpVersion()), new ResponseHeader());

            Controller controller = new ControllerMapper().map(request.extractUrl());
            controller.service(request, response);

            ResponseWriter.write(new DataOutputStream(out), response);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
