package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DataConverter;
import utils.FileLoader;
import utils.IOUtils;
import utils.exception.InvalidFileAccessException;
import web.controller.Controller;
import web.controller.impl.LoginController;
import web.controller.impl.UserController;
import web.controller.impl.UserListController;
import webserver.message.HttpStatus;
import webserver.message.exception.NotFoundFileException;
import webserver.message.exception.UrlDecodeException;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.message.response.ResponseBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.NoSuchElementException;
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
        try {
            final Request request = new Request(ioUtils);
            final Response response = new ResponseBuilder(request.getHttpVersion()).build();

            processResponse(request, response);

            return response.toBytes();
        } catch (IOException | URISyntaxException | NullPointerException | UrlDecodeException e) {
            return DataConverter.convertTo500Response(FileLoader.loadInternalServerErrorFile()).toBytes();
        }
    }

    private static void processResponse(final Request request, final Response response) {
        try {
            Optional<Controller> maybeHandler = getHandler(request);
            maybeHandler.get().service(request, response);
        } catch (NoSuchElementException e) {
            getStaticResponse(request, response);
        }
    }

    private static void getStaticResponse(Request request, Response response) {
        try {
            response.body(FileLoader.loadStaticFile(request.getPath()));
        } catch (NotFoundFileException | InvalidFileAccessException e) {
            logger.debug("404 Not Found: {}", e.toString());

            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.body(FileLoader.loadNotFoundFile());
        }
    }

    private static Optional<Controller> getHandler(final Request request) {
        return requestUrls.keySet().stream()
                .filter(url -> url.equals(request.getPath()))
                .map(requestUrls::get)
                .findFirst();
    }
}
