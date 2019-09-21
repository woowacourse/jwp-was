package http.common;

import java.util.Arrays;

public enum UriExtension {
    HTML("html"), ICO("ico"), CSS("css"), JS("js"), NONE("none");

    private static final String TEMPLATE_URI_PREFIX = "./templates";
    private static final String STATIC_URI_PREFIX = "./static";

    private String extension;
    private String pathPrefix;

    static {
        HTML.pathPrefix = TEMPLATE_URI_PREFIX;
        ICO.pathPrefix = TEMPLATE_URI_PREFIX;
        CSS.pathPrefix = STATIC_URI_PREFIX;
        JS.pathPrefix = STATIC_URI_PREFIX;
        NONE.pathPrefix = STATIC_URI_PREFIX;
    }

    UriExtension(String extension) {
        this.extension = extension;
    }

    public static UriExtension of(String uri) {
        return Arrays.stream(UriExtension.values())
            .filter(uriExtension -> uri.contains(uriExtension.extension))
            .findFirst()
            .orElse(UriExtension.NONE);
    }

    public String getPathPrefix() {
        return pathPrefix;
    }
}
