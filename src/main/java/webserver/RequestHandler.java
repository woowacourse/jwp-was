package webserver;

import http.ContentType;
import http.ContentTypeFactory;
import http.NotAcceptableException;
import http.request.HttpRequest;
import http.response.DataOutputStreamWrapper;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.page.HandlebarsPage;
import webserver.page.Page;
import webserver.pageprovider.PageProvider;
import webserver.pageprovider.PageProviderRequest;
import webserver.pageprovider.PageProviderResponse;
import webserver.router.RouterFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.of(in);
            DataOutputStreamWrapper dos = new DataOutputStreamWrapper(new DataOutputStream(out));
            HttpResponse httpResponse = HttpResponse.of(dos);

            tryHandleRequest(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void tryHandleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            PageProvider pageProvider = route(httpRequest);
            Page page = pageProvider.provide(PageProviderRequest.from(httpRequest), PageProviderResponse.from(httpResponse));

            if (page.isRedirectPage()) {
                httpResponse.redirect(page.getLocation());
                return;
            }

            respondPage(httpRequest, httpResponse, page);
        } catch (RuntimeException e) {
            logger.error("runtime error: ", e);
            respondErrorPage(httpRequest, httpResponse, e);
        }
    }

    private PageProvider route(HttpRequest httpRequest) {
        String path = httpRequest.getPath();

        return RouterFactory.getRouter().retrieve(path);
    }

    private void respondErrorPage(HttpRequest request, HttpResponse response, RuntimeException e) {
        response.clear();

        Page page = HandlebarsPage.locationWithObj("error/error_500", createHashMapWithError(e));

        respondPage(request, response, page);
    }

    private HashMap<String, String> createHashMapWithError(RuntimeException e) {
        return new HashMap<String, String>() {{
            put("error", e.getMessage());
        }};
    }

    private void respondPage(HttpRequest request, HttpResponse response, Page page) {
        validateContentType(request, page.getContentType());

        response.forward(page);
    }

    private void validateContentType(HttpRequest request, ContentType wantedContentType) {
        String accept = request.getHeader("Accept");
        if (!ContentTypeFactory.canCreate(accept, wantedContentType)) {
            throw NotAcceptableException.from(accept);
        }
    }
}
