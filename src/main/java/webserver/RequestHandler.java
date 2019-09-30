package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.View;
import view.excpetion.ExceptionViewResolver;
import view.internal.HandlerBarsViewResolver;
import view.internal.InternalResourceViewResolver;
import view.statics.StaticResourceMapping;
import view.statics.StaticViewResolver;
import webserver.http.Cookie;
import webserver.http.Cookies;
import webserver.http.response.HttpStatus;
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
    private ServletMapping servletMapping;
    private StaticViewResolver staticViewResolver;
    private InternalResourceViewResolver internalResourceViewResolver;
    private ExceptionViewResolver exceptionViewResolver;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.servletMapping = ServletMapping.getInstance();
        this.staticViewResolver = new StaticViewResolver(new StaticResourceMapping());
        this.internalResourceViewResolver = new HandlerBarsViewResolver();
        this.exceptionViewResolver = new ExceptionViewResolver();
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpRequest httpRequest = HttpRequestFactory.generate(in);
            final HttpResponse httpResponse = new HttpResponse(httpRequest, out);

            final View view = renderView(httpRequest, httpResponse);

            if (view.isNotEmpty()) {
                httpResponse.write(view.getBody());
                return;
            }
            httpResponse.write();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private View renderView(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        final String path = httpRequest.getPath();

        if (staticViewResolver.isStaticFile(path)) {
            return staticViewResolver.resolveView(path, httpResponse);
        }

        if (servletMapping.isMapping(path)) {
            final Servlet servlet = servletMapping.getServlet(path);
            servlet.service(httpRequest, httpResponse);
            return internalResourceViewResolver.resolveView(httpResponse);
        }
        return exceptionViewResolver.resolveView(HttpStatus.NOT_FOUND, httpResponse);
    }
}
