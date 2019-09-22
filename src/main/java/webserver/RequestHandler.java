package webserver;

import http.request.Request;
import http.response.Response;
import http.response.ResponseHeader;
import http.utils.RequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.support.MethodHandler;

import java.io.*;
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
            Response response = new Response(new ResponseHeader());

            MethodHandler handler = new MethodHandler();
            handler.handle(request, response);

            response.writeMessage(new DataOutputStream(out));
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
