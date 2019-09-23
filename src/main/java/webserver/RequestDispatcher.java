package webserver;

import controller.UserController;
import utils.DataConverter;
import utils.FileIoUtils;
import utils.FileLoader;
import utils.IOUtils;
import utils.exception.InvalidFileAccessException;
import webserver.message.exception.UrlDecodeException;
import webserver.message.request.Request;
import webserver.message.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class RequestDispatcher {
    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    private static final Map<String, Function<Request, Response>> requestUrls = new HashMap<>();

    static {
        requestUrls.put("/user/create", UserController::createUser);
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
            final Response response = requestUrls.getOrDefault(request.getPath(), RequestDispatcher::serveResponse).apply(request);
            return Objects.nonNull(response) ? DataConverter.convertToBytes(response) :
                    DataConverter.convertToBytes(FileIoUtils.loadFileFromClasspath(makeFilePath(request, STATIC_PATH)));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            return DataConverter.convertToBytes(FileIoUtils.loadFileFromClasspath(makeFilePath(request, TEMPLATES_PATH)));
        }
    }

    private static Response serveResponse(Request request) {
        try {
            return DataConverter.convertTo200Response(FileLoader.loadStaticFile(request));
        } catch (InvalidFileAccessException e) {
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
