package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpStatus;
import webserver.http.handler.StaticResourceHandler;
import webserver.http.handler.StaticResourceMapping;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestFactory;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private StaticResourceHandler staticResourceHandler;
    private ServletMapping servletMapping;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        staticResourceHandler = new StaticResourceHandler(new StaticResourceMapping());
        servletMapping = ServletMapping.getInstance();
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpRequest httpRequest = HttpRequestFactory.generate(in);
            final HttpResponse httpResponse = new HttpResponse(out);

            // TODO 정적 파일 (css, html, js)
            final String path = httpRequest.getPath();
            if (staticResourceHandler.isMapping(path)) {
                staticResourceHandler.handle(httpRequest, httpResponse);
                return;
            }

            // TODO 동적 처리 (servlet or controller)
            final Servlet servlet = servletMapping.getServlet(path);
            servlet.service(httpRequest, httpResponse);

            // TODO 404
            httpResponse.sendError(HttpStatus.NOT_FOUND);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
