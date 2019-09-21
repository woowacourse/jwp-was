package http;

import java.util.Arrays;

public enum UriExtension {
    HTML("html"), ICO("ico"), CSS("css"), JS("js"), NONE("none");

    private static final String TEMPLATE_URI_PREFIX = "./templates";
    private static final String STATIC_URI_PREFIX = "./static";
    private static final String HTML_CONTENT_TYPE = "text/html";
    private static final String CSS_CONTENT_TYPE = "text/css";
    private static final String JAVASCRIPT_CONTENT_TYPE = "text/javascript";

    private String extension;
    private String pathPrefix;
    private String contentType;

    static {
        HTML.pathPrefix = TEMPLATE_URI_PREFIX;
        ICO.pathPrefix = TEMPLATE_URI_PREFIX;
        CSS.pathPrefix = STATIC_URI_PREFIX;
        JS.pathPrefix = STATIC_URI_PREFIX;
        NONE.pathPrefix = STATIC_URI_PREFIX;

        HTML.contentType = HTML_CONTENT_TYPE;
        ICO.contentType = HTML_CONTENT_TYPE;
        CSS.contentType = CSS_CONTENT_TYPE;
        JS.contentType = JAVASCRIPT_CONTENT_TYPE;
        NONE.contentType = HTML_CONTENT_TYPE;
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

    public String getExtension() {
        return extension;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }

    public String getContentType() {
        return contentType;
    }
}
