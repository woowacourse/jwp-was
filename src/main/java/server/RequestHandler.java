package server;

import was.http.*;
import server.http.io.HttpHandler;
import server.http.request.HttpRequest;
import server.http.resource.ResourceHandler;
import server.http.resource.StaticResourceHandler;
import server.http.response.HttpResponse;
import was.http.servlet.Servlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private static final Router ROUTER = Router.getInstance();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        LOGGER.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpHandler.parse(in);
            HttpResponse httpResponse = new HttpResponse();

            ResourceHandler handler = StaticResourceHandler.getInstance();
            if (handler.canHandle(httpRequest)) {
                httpResponse = handler.handle(httpRequest);
            }

            Servlet servlet = ROUTER.getServlet(httpRequest.getPath());
            if (servlet != null) {
                httpResponse = servlet.service(httpRequest);
            }

            // TODO: 404 NOT FOUND
            httpResponse.setHttpVersion(httpRequest.getHttpVersion());
            HttpHandler.send(out, httpResponse);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
