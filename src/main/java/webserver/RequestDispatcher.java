package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileLoader;
import utils.IOUtils;
import utils.exception.NoSuchFileException;
import web.controller.Controller;
import web.controller.impl.LoginController;
import web.controller.impl.UserController;
import web.controller.impl.UserListController;
import webserver.message.HttpStatus;
import webserver.message.HttpVersion;
import webserver.message.request.Request;
import webserver.message.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class RequestDispatcher {
    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);

    private static final Map<String, Controller> requestUrls = new ConcurrentHashMap<>();

    static {
        requestUrls.put("/user/create", new UserController());
        requestUrls.put("/user/login", new LoginController());
        requestUrls.put("/user/list", new UserListController());
    }

    public static byte[] forward(final IOUtils ioUtils) {
        final Request request;
        final Response response = new Response();

        try {
            request = new Request(ioUtils);
            processResponse(request, response);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            logger.debug("500 Internal Server Error: {}", e.toString());
            internalServerError500Response(response);
        }
        return response.toBytes();
    }

    private static void processResponse(final Request request, final Response response) {
        response.setHttpVersion(HttpVersion.of(request.getHttpVersion()));

        Optional<Controller> maybeHandler = getHandler(request);
        maybeHandler.ifPresentOrElse(controller -> controller.service(request, response),
                () -> processResponseBody(request, response));
    }

    private static void processResponseBody(final Request request, final Response response) {
        try {
            response.body(FileLoader.loadFile(request.getPath()));
        } catch (NoSuchFileException e) {
            logger.debug("404 Not Found: {}", e.toString());
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.body(FileLoader.loadNotFoundFile());
        } catch (IOException | URISyntaxException e) {
            logger.debug("500 Internal Server Error: {}", e.toString());
            internalServerError500Response(response);
        }
    }

    private static void internalServerError500Response(final Response response) {
        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.body(FileLoader.loadInternalServerErrorFile());
    }

    private static Optional<Controller> getHandler(final Request request) {
        return requestUrls.keySet().stream()
                .filter(url -> url.equals(request.getPath()))
                .map(requestUrls::get)
                .findFirst();
    }
}
