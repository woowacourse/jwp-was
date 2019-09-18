package webserver.request.requestline;

import java.util.Arrays;

public enum RequestUriExtension {
    HTML("html"), ICO("ico"), CSS("css"), JS("js"), NONE("none");

    private static final String TEMPLATE_URI_PREFIX = "./templates";
    private static final String STATIC_URI_PREFIX = "./static";

    private String extension;
    private String uriPrefix;

    static {
        HTML.uriPrefix = TEMPLATE_URI_PREFIX;
        ICO.uriPrefix = TEMPLATE_URI_PREFIX;
        CSS.uriPrefix = STATIC_URI_PREFIX;
        JS.uriPrefix = STATIC_URI_PREFIX;
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
}
