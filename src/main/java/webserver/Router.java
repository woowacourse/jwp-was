package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.FileIoUtils;
import utils.parser.KeyValueParserFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.headerfields.HttpContentType;
import webserver.http.startline.HttpMethod;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

public class Router {
    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    private static final Map<String, String> GET_ROUTER =
            FileIoUtils.loadFileFromClasspath("./get-route.wwml").map(config ->
                    KeyValueParserFactory.routerParser().interpret(config)
            ).orElse(null);
    private static final Map<String, String> POST_ROUTER =
            FileIoUtils.loadFileFromClasspath("./post-route.wwml").map(config ->
                    KeyValueParserFactory.routerParser().interpret(config)
            ).orElse(null);

    public static HttpResponse serve(HttpRequest req) {
        return (req.path().extension().isEmpty()) ? route(req) : serveStaticFiles(req);
    }

    private static HttpResponse route(HttpRequest req) {
        final String mapping = (req.method() == HttpMethod.GET)
                ? GET_ROUTER.get(req.path().toString())
                : POST_ROUTER.get(req.path().toString());
        logger.debug("Route: {} -> {}", req.path(), mapping);
        return Optional.ofNullable(mapping).map(routeTo -> {
            final String[] classAndMethodNames = routeTo.split("\\.");
            try {
                return (HttpResponse) Class.forName("controller." + classAndMethodNames[0])
                                            .getMethod(classAndMethodNames[1], HttpRequest.class)
                                            .invoke(null, req);
            } catch (
                    ClassNotFoundException |
                    NoSuchMethodException |
                    IllegalAccessException |
                    InvocationTargetException e
            ) {
                logger.debug(e.getMessage());
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
