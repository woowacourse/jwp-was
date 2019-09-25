package webserver;

import controller.UserController;
import org.slf4j.Logger;
import webserver.domain.Request;
import webserver.domain.Response;
import webserver.domain.StaticFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.slf4j.LoggerFactory.getLogger;

public class RequestDispatcher {
    private static final Logger LOG = getLogger(RequestDispatcher.class);
    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";
    private static final String NOT_FOUND_PAGE_PATH = TEMPLATES_PATH + "/error/404_not_found.html";
    private static final Map<String, Function<Request, Response>> requestUrls = new HashMap<>();
    private static StaticFile NOT_FOUND_PAGE;

    static {
        requestUrls.put("/user/create", UserController::createUser);
        try {
            NOT_FOUND_PAGE = new StaticFile(NOT_FOUND_PAGE_PATH);
        } catch (IOException | URISyntaxException e) {
            NOT_FOUND_PAGE = null;
        }
    }

    public static Response forward(final Request request) {
        return requestUrls.getOrDefault(request.getPath(), RequestDispatcher::serveStaticFile).apply(request);
    }

    private static Response serveStaticFile(final Request request) {
        return convertToResponse(getFile(request));
    }

    private static StaticFile getFile(final Request request) {
        StaticFile file;
        try {
            file = tryStaticFileRead(request);
        } catch (IOException | NullPointerException | URISyntaxException e) {
            file = NOT_FOUND_PAGE;
        }
        return file;
    }

    private static StaticFile tryStaticFileRead(final Request request) throws IOException, URISyntaxException {
        try {
            return new StaticFile(makeFilePath(request, STATIC_PATH));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            return new StaticFile(makeFilePath(request, TEMPLATES_PATH));
        }
    }

    // TODO: 추후 분리
    private static String makeFilePath(final Request requestHeader, final String prefix) {
        final String requestPath = requestHeader.getPath();
        final String pathEnd = (requestPath.endsWith("/") || "".equals(requestPath)) ? "index.html" : "";
        return prefix + requestPath + pathEnd;
    }

    private static Response convertToResponse(final StaticFile file) {
        return new Response.Builder().body(file).contentType(file.getMediaType()).build();
    }
}
