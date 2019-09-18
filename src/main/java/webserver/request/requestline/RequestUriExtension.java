package webserver.request.requestline;

import java.util.Arrays;

public enum RequestUriExtension {
    HTML("html"), ICO("ico"), CSS("css"), JS("js"), NONE("none");

    private static final String TEMPLATE_URI_PREFIX = "./templates";
    private static final String STATIC_URI_PREFIX = "./static";
    private static final String HTML_CONTENT_TYPE = "text/html";
    private static final String CSS_CONTENT_TYPE = "text/css";
    private static final String JAVASCRIPT_CONTENT_TYPE = "text/javascript";

    private String extension;
    private String uriPrefix;
    private String contentType;

    static {
        HTML.uriPrefix = TEMPLATE_URI_PREFIX;
        ICO.uriPrefix = TEMPLATE_URI_PREFIX;
        CSS.uriPrefix = STATIC_URI_PREFIX;
        JS.uriPrefix = STATIC_URI_PREFIX;
        NONE.uriPrefix = STATIC_URI_PREFIX;

        HTML.contentType = HTML_CONTENT_TYPE;
        ICO.contentType = HTML_CONTENT_TYPE;
        CSS.contentType = CSS_CONTENT_TYPE;
        JS.contentType = JAVASCRIPT_CONTENT_TYPE;
        NONE.contentType = HTML_CONTENT_TYPE;
    }

    RequestUriExtension(String extension) {
        this.extension = extension;
    }

    public static RequestUriExtension findExtension(String extension) {
        return Arrays.stream(RequestUriExtension.values())
            .filter(e -> e.isSame(extension))
            .findFirst()
            .orElse(RequestUriExtension.NONE);
    }

    public boolean isSame(String extension) {
        return this.extension.equals(extension);
    }

    public String getUriPrefix() {
        return uriPrefix;
    }

    public String getContentType() {
        return contentType;
    }
}
