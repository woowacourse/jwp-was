package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.view.*;
import webserver.http.HttpStatus;
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
    private StaticViewResolver staticViewResolver;
    private InternalResourceViewResolver internalResourceViewResolver;

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

            final String path = httpRequest.getPath();
            View view;
            if (staticViewResolver.isStaticFile(path)) {
                view = staticViewResolver.resolveViewName(path);
            } else if (servletMapping.isMapping(path)) {
                final Servlet servlet = servletMapping.getServlet(path);
                servlet.service(httpRequest, httpResponse);
                view = internalResourceViewResolver.resolveViewName(httpResponse.g);
            } else {
                httpResponse.sendError(HttpStatus.NOT_FOUND);
            }



            // isMapping
            // viewResolver.resolveViewName(path)
            // .. 바디 있고 없음에 따라 다르게 호출

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
