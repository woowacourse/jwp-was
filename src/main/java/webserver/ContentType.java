package webserver;

import java.util.Arrays;

public enum ContentType {
    TEXT_HTML("html", "text/html"),
    TEXT_CSS("css", "text/css"),
    APPLICATION_JAVASCRIPT("js", "application/javascript");

    private final String extension;
    private final String value;

    ContentType(String extension, String value) {
        this.extension = extension;
        this.value = value;
    }

    public static String value(String path) {
        return Arrays.stream(values())
            .filter(type -> path.endsWith(type.extension))
            .findAny()
            .map(type -> type.value)
            .orElse(TEXT_HTML.value);
    }
}
