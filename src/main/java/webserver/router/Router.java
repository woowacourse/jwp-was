package webserver.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.FileIoUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.http.HttpContentType;

import java.util.Optional;

public class Router {
    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    private static final Router instance = RouterConfig.init().map(Router::new).orElse(null);

    private final RouterConfig config;

    public static Optional<Router> init() {
        return Optional.ofNullable(instance);
    }

    private Router(RouterConfig config) {
        this.config = config;
    }

    public HttpResponse serve(HttpRequest req) {
        return (req.path().extension().isEmpty()) ? route(req) : serveStaticFiles(req);
    }

    private HttpResponse route(HttpRequest req) {
        return config.match(req.method(), req.path()).map(dest -> {
            logger.debug("Route: {} -> {}", req.path(), dest);
            return dest.execute(req);
        }).orElse(HttpResponse.NOT_FOUND);
    }

    private HttpResponse serveStaticFiles(HttpRequest req) {
        final String dest = "./static" + req.path();
        logger.debug("Route: {} -> {}", req.path(), dest);
        return FileIoUtils.loadFileFromClasspath(dest).map(body ->
            HttpResponse.builder(extensionToContentType(req.path().extension()))
                        .extractFromRequest(req)
                        .body(body)
                        .build()
        ).orElse(HttpResponse.NOT_FOUND);
    }

    private HttpContentType extensionToContentType(String extension) {
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
