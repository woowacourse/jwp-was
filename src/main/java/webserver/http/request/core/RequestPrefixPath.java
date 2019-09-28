package webserver.http.request.core;

import java.util.Arrays;

public enum RequestPrefixPath {
    CSS("/css", "../resources/static"),
    FONT("/fonts", "../resources/static"),
    JAVASCRIPT("/js", "../resources/static"),
    IMAGE("/images", "../resources/static"),
    UNDEFINED("undefined", "../resources/templates");

    private final String requestPath;
    private final String pathPrefix;

    RequestPrefixPath(String requestPath, String pathPrefix) {
        this.requestPath = requestPath;
        this.pathPrefix = pathPrefix;
    }

    public static RequestPrefixPath of(String requestPath) {
        return Arrays.stream(RequestPrefixPath.values())
                .filter(path -> requestPath.contains(path.getRequestPath()))
                .findAny()
                .orElse(UNDEFINED);
    }

    private String getRequestPath() {
        return requestPath;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }
}
