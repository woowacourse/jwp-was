package webserver.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.NotMatchUrlException;

public class PathHandler {
    private static final Logger log = LoggerFactory.getLogger(PathHandler.class);
    private static final String TEMPLATE_URL_FORMAT = "./templates%s";
    private static final String STATIC_URL_FORMAT = "./static%s";
    private static final String REGEX_FOR_TEMPLATES = "/?[A-Za-z0-9/.\\-_]+\\.(html|ico)";
    private static final String REGEX_FOR_STATIC = "/?[A-Za-z0-9/.\\-_]+\\.(css|js|ttf|woff)";

    public static String path(String url) {
        log.debug("url in PathHandler : {}", url);
        if (url.matches(REGEX_FOR_TEMPLATES)) {
            return String.format(TEMPLATE_URL_FORMAT, url);
        }

        if (url.matches(REGEX_FOR_STATIC)) {
            return String.format(STATIC_URL_FORMAT, url);
        }

        throw new NotMatchUrlException();
    }
}
