package webserver;

import java.util.Arrays;
import java.util.function.Predicate;

public enum ContentType {
    HTML("text/html", containsPath("html")),
    ICON("image/x-icon", containsPath("ico")),
    CSS("text/css", containsPath("css")),
    JS("text/javascript", containsPath("js")),
    IMAGE("image/png", containsPath("png")),
    FONT_WOFF("application/x-font-woff", containsPath("woff")),
    FONT_TTF("application/x-font-ttf", containsPath("ttf"));

    private static final String CHARSET = "charset=utf-8";

    private final String contentType;
    private final Predicate<String> predicate;

    ContentType(String contentType, Predicate<String> predicate) {
        this.contentType = contentType;
        this.predicate = predicate;
    }

    public static ContentType form(String path) {
        return Arrays.stream(values())
                .filter(contentType -> contentType.predicate.test(path))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ContentType" + path));
    }

    private static Predicate<String> containsPath(String type) {
        return path -> path.contains(type);
    }

    public String get() {
        return String.format("%s; %s", contentType, CHARSET);
    }
}
