package webserver;

import controller.UserController;
import utils.DataConverter;
import utils.FileLoader;
import webserver.message.exception.UrlDecodeException;
import webserver.message.request.Request;
import webserver.message.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RequestDispatcher {
    private static final Map<String, Function<Request, Response>> requestUrls = new HashMap<>();

    static {
        requestUrls.put("/user/create", UserController::createUser);
    }

    public static Response forward(final Request request) {
        return requestUrls.getOrDefault(request.getPath(), RequestDispatcher::serveResponse).apply(request);
    }

    private static Response serveResponse(Request request) {
        try {
            return DataConverter.convertTo200Response(FileLoader.loadStaticFile(request));
        } catch (IOException | URISyntaxException | NullPointerException | UrlDecodeException e) {
            return DataConverter.convertTo404Response(FileLoader.loadNotFoundFile());
        }
    }
}
