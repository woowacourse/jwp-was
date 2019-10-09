package webserver;

import utils.DataConverter;
import utils.FileLoader;
import utils.IOUtils;
import web.controller.Controller;
import web.controller.impl.LoginController;
import web.controller.impl.UserController;
import web.controller.impl.UserListController;
import webserver.message.exception.NotFoundFileException;
import webserver.message.exception.UrlDecodeException;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.message.response.ResponseBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class RequestDispatcher {
    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

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

            getHandler(request, response);

            return response.toBytes();
        } catch (IOException | URISyntaxException | NullPointerException | UrlDecodeException e) {
            return DataConverter.convertTo500Response(FileLoader.loadInternalServerErrorFile()).toBytes();
        }
    }

    private static void getHandler(final Request request, final Response response) {
        try {
            requestUrls.keySet().stream()
                    .filter(url -> url.equals(request.getPath()))
                    .map(requestUrls::get)
                    .findFirst()
                    .get()
                    .service(request, response);
            ;
        } catch (NoSuchElementException e) {
            response.ok(makeFilePath(request, TEMPLATES_PATH));
        } catch (NotFoundFileException e) {
            response.notFound();
        }
    }

    /*private static Response serveResponse(Request request) {
        try {
            return DataConverter.convertTo200Response(FileLoader.loadStaticFile(request));
        } catch (InvalidFileAccessException | NotFoundFileException e) {
            return DataConverter.convertTo404Response(FileLoader.loadNotFoundFile());
        } catch (NullPointerException | UrlDecodeException e) {
            return DataConverter.convertTo500Response(FileLoader.loadInternalServerErrorFile());
        }
    }*/

    private static String makeFilePath(final Request request, final String prefix) {
        final String requestPath = request.getPath();
        final String pathEnd = (requestPath.endsWith("/") || "".equals(requestPath)) ? "index.html" : "";
        return prefix + requestPath + pathEnd;
    }
}
