package webserver.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.FileIoUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.httpelement.HttpContentType;

import java.util.Optional;

public class Router {
    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    private static final Router instance = RouterConfig.init().map(Router::new).orElse(null);

    public static final String STATIC_FILE_PATH = "./static";

    private final RouterConfig config;

    public static Optional<Router> init() {
        return Optional.ofNullable(instance);
    }

    private Router(RouterConfig config) {
        this.config = config;
    }

    public HttpResponse serve(HttpRequest req) {
        return (req.path().extension().isEmpty()) ? routeTo(req) : serveStaticFiles(req);
    }

    private HttpResponse routeTo(HttpRequest req) {
        return config.match(req.method(), req.path()).map(dest -> {
            logger.debug("Route: {} -> {}", req.path(), dest);
            return dest.execute(req);
        }).orElse(HttpResponse.NOT_FOUND);
    }

    private HttpResponse serveStaticFiles(HttpRequest req) {
        final String dest = STATIC_FILE_PATH + req.path();
        logger.debug("Route: {} -> {}", req.path(), dest);
        return FileIoUtils.loadFileFromClasspath(dest).map(body ->
            HttpResponse.builder(HttpContentType.fromFileExtension(req.path().extension()))
                        .extractFieldsFromRequest(req)
                        .body(body)
                        .build()
        ).orElse(HttpResponse.NOT_FOUND);
    }
}
