package web;

import java.util.Arrays;
import java.util.Optional;

public enum ResourceMatcher {
    HTML(".html", "./templates", "text/html;charset=UTF-8"),
    ICO(".ico", "./templates", "*/*"),
    CSS(".css", "./static", "text/css;charset=UTF-8"),
    JS(".js", "./static", "application/javascript;charset=UTF-8"),
    AVG(".avg", "./static", "*/*"),
    TTF(".ttf", "./static", "*/*"),
    WOFF(".woff", "./static", "*/*"),
    WOFF2(".woff2", "./static", "*/*");

    private final String extension;
    private final String pathPrefix;
    private final String contentType;

    ResourceMatcher(String extension, String pathPrefix, String contentType) {
        this.extension = extension;
        this.pathPrefix = pathPrefix;
        this.contentType = contentType;
    }

    public static Optional<ResourceMatcher> fromUri(String uri) {
        return Arrays.stream(values())
                .filter(value -> uri.endsWith(value.extension))
                .findFirst();
    }

    public String getResourcePath() {
        return pathPrefix;
    }

    public String getContentType() {
        return contentType;
    }
}