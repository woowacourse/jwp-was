package webserver;

import java.util.Arrays;
import java.util.function.Predicate;

public enum ContentType {
    HTML("text/html; charset=utf-8", path -> path.contains("html")),
    ICON("image/x-icon; charset=utf-8", path -> path.contains("ico")),
    CSS("text/css; charset=utf-8", path -> path.contains("css")),
    JS("text/javascript; charset=utf-8", path -> path.contains("js")),
    IMAGE("image/png; charset=utf-8", path -> path.contains("png")),
    FONT_WOFF("application/x-font-woff; charset=utf-8", path -> path.contains("woff")),
    FONT_TTF("application/x-font-ttf; charset=utf-8", path -> path.contains("ttf"));

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

    public String get() {
        return contentType;
    }
}
