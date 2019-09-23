package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.headerfields.HttpContentType;

import java.lang.reflect.InvocationTargetException;

public class RoutingHandler {
    private static final Logger logger = LoggerFactory.getLogger(RoutingHandler.class);

    private static final Router router = Router.getInstance();

    public static HttpResponse serve(HttpRequest req) {
        return (req.path().extension().isEmpty()) ? route(req) : serveStaticFiles(req);
    }

    private static HttpResponse route(HttpRequest req) {
        return router.routeTo(req.method(), req.path().toString()).map(dest -> {
            logger.debug("Route: {} -> {}", req.path(), dest);
            try {
                return (HttpResponse) Class.forName("controller." + dest.controller())
                                            .getMethod(dest.method(), HttpRequest.class)
                                            .invoke(null, req);
            } catch (
                    ClassNotFoundException |
                    NoSuchMethodException |
                    IllegalAccessException |
                    InvocationTargetException e
            ) {
                logger.error(e.getMessage());
                return HttpResponse.INTERNAL_SERVER_ERROR;
            }
        }).orElse(HttpResponse.NOT_FOUND);
    }

    private static HttpResponse serveStaticFiles(HttpRequest req) {
        return FileIoUtils.loadFileFromClasspath("./static" + req.path()).map(body ->
            HttpResponse.builder(extensionToContentType(req.path().extension()))
                        .version(req)
                        .connection(req)
                        .body(body)
                        .build()
        ).orElse(HttpResponse.NOT_FOUND);
    }

    private static HttpContentType extensionToContentType(String extension) {
        switch (extension) {
            case "html":
                return HttpContentType.TEXT_HTML_UTF_8;
            case "css":
                return HttpContentType.TEXT_CSS_UTF_8;
            case "js":
                return HttpContentType.APPLICATION_JAVASCRIPT_UTF_8;
            case "bmp":
                return HttpContentType.IMAGE_BMP;
            case "gif":
                return HttpContentType.IMAGE_GIF;
            case "jpg":
                return HttpContentType.IMAGE_JPEG;
            case "png":
                return HttpContentType.IMAGE_PNG;
            case "ico":
                return HttpContentType.IMAGE_X_ICON;
            case "txt":
            default:
                return HttpContentType.TEXT_PLAIN_UTF_8;
        }
    }
}
