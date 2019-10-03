package http.common;

import java.util.Arrays;

public enum UriExtension {
    HTML("html"),
    ICO("ico"),
    CSS("css"),
    JS("js"),
    WOFF("woff"),
    TTF("ttf"),
    UNDEFINED("undefined");

    private static final String TEMPLATE_URI_PREFIX = "./templates";
    private static final String STATIC_URI_PREFIX = "./static";
    private String extension;
    private String pathPrefix;

    static {
        HTML.pathPrefix = TEMPLATE_URI_PREFIX;
        ICO.pathPrefix = TEMPLATE_URI_PREFIX;
        CSS.pathPrefix = STATIC_URI_PREFIX;
        JS.pathPrefix = STATIC_URI_PREFIX;
        WOFF.pathPrefix = STATIC_URI_PREFIX;
        TTF.pathPrefix = STATIC_URI_PREFIX;
        UNDEFINED.pathPrefix = STATIC_URI_PREFIX;
    }

    UriExtension(String extension) {
        this.extension = extension;
    }

    public static UriExtension of(String uri) {
        return Arrays.stream(UriExtension.values())
            .filter(uriExtension -> {
                String[] uriTokens = uri.split("\\.");
                return uriExtension.extension.equals(uriTokens[uriTokens.length - 1]);
            })
            .findFirst()
            .orElse(UriExtension.UNDEFINED);
    }

    public String getPathPrefix() {
        return pathPrefix;
    }
}
