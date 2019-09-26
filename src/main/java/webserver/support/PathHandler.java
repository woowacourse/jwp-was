package webserver.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.NotMatchUrlException;

public class PathHandler {
    private static final Logger log = LoggerFactory.getLogger(PathHandler.class);
    private static final String TEMPLATE_URL_FORMAT = "./templates%s";
    private static final String STATIC_URL_FORMAT = "./static%s";
    private static final String START_POINT_OF_URL = "/";
    private static final String REGEX_FOR_TEMPLATES = "/?[A-Za-z0-9/.\\-_]+\\.(html|ico)";
    private static final String REGEX_FOR_STATIC = "/?[A-Za-z0-9/.\\-_]+\\.(css|js|ttf|woff|png)";

    public static String path(String url) {
        log.debug("url in PathHandler : {}", url);
        String parsedUrl = url = parseUrl(url);

        if (parsedUrl.matches(REGEX_FOR_TEMPLATES)) {
            return String.format(TEMPLATE_URL_FORMAT, url);
        }

        if (parsedUrl.matches(REGEX_FOR_STATIC)) {
            return String.format(STATIC_URL_FORMAT, url);
        }

        throw new NotMatchUrlException(parsedUrl + "에 맵핑되는 자원이 없습니다");
    }

    private static String parseUrl(String url) {
        int index = url.indexOf(START_POINT_OF_URL);
        return url.substring(index);
    }
}
