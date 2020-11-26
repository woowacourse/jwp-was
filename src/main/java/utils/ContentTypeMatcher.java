package utils;

import java.util.Arrays;
import java.util.function.Predicate;

public enum ContentTypeMatcher {
    TEXT_HTML("text/html", value -> value.endsWith(".html")),
    TEXT_CSS("text/css", value -> value.endsWith(".css")),
    TEXT_JAVASCRIPT("text/javascript", value -> value.endsWith(".js")),
    IMAGE_SVG_XML("image/svg+xml", value -> value.endsWith(".svg")),
    APPLICATION_VND_MS_FONTOBJECT("application/vnd.ms-fontobject", value -> value.endsWith(".eof")),
    FONT_TTF("font/ttf", value -> value.endsWith(".ttf")),
    FONT_WOFF("font/woff", value -> value.endsWith(".woff")),
    FONT_WOFF2("font/woff2", value -> value.endsWith(".woff2")),
    IMAGE_PNG("image/png", value -> value.endsWith(".png")),
    IMAGE_VND_MICROSOFT_ICON("image/vnd.microsoft.icon", value -> value.endsWith(".ico")),
    APPLICATION_OCTET_STREAM("application/octet-stream", value -> false);

    private final String contentType;
    private final Predicate<String> predicate;

    ContentTypeMatcher(String contentType, Predicate<String> predicate) {
        this.contentType = contentType;
        this.predicate = predicate;
    }

    public static String match(String value) {
        return Arrays.stream(values())
            .filter(it -> it.predicate.test(value))
            .findFirst()
            .orElse(APPLICATION_OCTET_STREAM)
            .contentType;
    }
}
