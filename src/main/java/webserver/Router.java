package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.FileIoUtils;
import utils.parser.KeyValueParserFactory;
import webserver.http.headerfields.HttpContentType;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class Router {
    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    public static HttpResponse serve(HttpRequest req) {
        final String path = req.path().toString();
        if (req.path().extension().isEmpty()) {
            return FileIoUtils.loadFileFromClasspath("./route.wwml").map(config ->
                route(req, KeyValueParserFactory.routerParser().toMap(config).get(path))
            ).orElse(HttpResponse.INTERNAL_SERVER_ERROR);
        }
        return serveStaticFiles(req);
    }

    private static HttpResponse route(HttpRequest req, String mapping) {
        return Optional.ofNullable(mapping).map(controller -> {
            final String[] names = controller.split(".");
            try {
                return (HttpResponse) Class.forName("controller." + names[0])
                                            .getMethod(names[1], HttpRequest.class)
                                            .invoke(null, req);
            } catch (
                    ClassNotFoundException |
                    NoSuchMethodException |
                    IllegalAccessException |
                    InvocationTargetException e
            ) {
                logger.debug(e.getMessage());
                return HttpResponse.NOT_FOUND;
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
            case "css":
                return HttpContentType.TEXT_CSS();
            case "js":
                return HttpContentType.APPLICATION_JAVASCRIPT();
            case "gif":
                return HttpContentType.IMAGE_GIF();
            case "jpg":
                return HttpContentType.IMAGE_JPEG();
            case "png":
                return HttpContentType.IMAGE_PNG();
            case "ico":
                return HttpContentType.IMAGE_X_ICON();
            case "txt":
            default:
                return HttpContentType.TEXT_PLAIN();
        }
    }
}
