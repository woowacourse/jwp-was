package webserver.controller;

import java.util.Arrays;

public enum ContentTypeMapper {
    HTML("html", ContentType.HTML),
    CSS("css", ContentType.CSS),
    JS("js", ContentType.JAVASCRIPT),
    TEXT("txt", ContentType.TEXT),
    APPLICATION_JSON("json", ContentType.APPLICATION_JSON),
    ICO("ico", ContentType.ICO),
    TTF("ttf", ContentType.TTF),
    WOFF("woff", ContentType.WOFF),
    WOFF2("woff2", ContentType.WOFF2);

    private final String extension;
    private final ContentType contentType;

    ContentTypeMapper(String extension, ContentType contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static ContentType map(String extension) {
        return Arrays.stream(values())
            .filter(value -> value.extension.equals(extension))
            .findAny()
            .orElseThrow(() -> new UndefinedExtensionException(extension))
            .contentType;
    }
}
