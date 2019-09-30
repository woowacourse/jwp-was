package webserver;

import utils.DataConverter;
import utils.FileIoUtils;
import utils.FileLoader;
import utils.IOUtils;
import utils.exception.InvalidFileAccessException;
import web.controller.Controller;
import web.controller.impl.LoginController;
import web.controller.impl.UserController;
import web.controller.impl.UserListController;
import webserver.message.exception.NotFoundFileException;
import webserver.message.exception.UrlDecodeException;
import webserver.message.request.Request;
import webserver.message.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;
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
            return processResponse(request);
        } catch (IOException | URISyntaxException | NullPointerException | UrlDecodeException e) {
            return DataConverter.convertTo500Response(FileLoader.loadInternalServerErrorFile()).toBytes();
        }
    }

    private static byte[] processResponse(final Request request) throws IOException, URISyntaxException {
        try {
            final Response response = requestUrls.entrySet().stream()
                    .filter(entry -> entry.getKey().equals(request.getPath()))
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .orElseGet(() -> RequestDispatcher::serveResponse)
                    .service(request);
            return Objects.nonNull(response) ? DataConverter.convertToBytes(response) :
                    DataConverter.convertToBytes(FileIoUtils.loadFileFromClasspath(makeFilePath(request, STATIC_PATH)));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            return DataConverter.convertToBytes(FileIoUtils.loadFileFromClasspath(makeFilePath(request, TEMPLATES_PATH)));
        } catch (NotFoundFileException e) {
            return DataConverter.convertTo404Response(FileLoader.loadNotFoundFile()).toBytes();
        }
    }

    private static Response serveResponse(Request request) {
        try {
            return DataConverter.convertTo200Response(FileLoader.loadStaticFile(request));
        } catch (InvalidFileAccessException | NotFoundFileException e) {
            return DataConverter.convertTo404Response(FileLoader.loadNotFoundFile());
        } catch (NullPointerException | UrlDecodeException e) {
            return DataConverter.convertTo500Response(FileLoader.loadInternalServerErrorFile());
        }
    }

    private static String makeFilePath(final Request requestHeader, final String prefix) {
        final String requestPath = requestHeader.getPath();
        final String pathEnd = (requestPath.endsWith("/") || "".equals(requestPath)) ? "index.html" : "";
        return prefix + requestPath + pathEnd;
    }
}
