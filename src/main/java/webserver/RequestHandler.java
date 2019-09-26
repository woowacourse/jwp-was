package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpStatus;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestFactory;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.Servlet;
import webserver.view.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private ServletMapping servletMapping;
    private StaticViewResolver staticViewResolver;
    private InternalResourceViewResolver internalResourceViewResolver;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.servletMapping = ServletMapping.getInstance();
        this.staticViewResolver = new StaticViewResolver(new StaticResourceMapping());
        this.internalResourceViewResolver = new HandlerBarsViewResolver();

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
                view = staticViewResolver.resolveViewName(path, httpResponse);
            } else if (servletMapping.isMapping(path)) {
                final Servlet servlet = servletMapping.getServlet(path);
                servlet.service(httpRequest, httpResponse);
                view = internalResourceViewResolver.resolveViewName(httpResponse);
            } else {
                httpResponse.sendError(HttpStatus.NOT_FOUND);
                view = EmptyView.getInstance();
            }

            if (view.isEmpty()) {
                httpResponse.write();
            } else {
                httpResponse.write(view.getBody());
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
