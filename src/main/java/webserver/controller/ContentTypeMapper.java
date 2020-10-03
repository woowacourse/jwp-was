package webserver.controller;

import static webserver.controller.ContentType.*;

import java.util.Arrays;

public enum ContentTypeMapper {
    HTML("html", ContentType.HTML),
    CSS("css", ContentType.CSS),
    JS("js", JAVASCRIPT);

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
            .orElseThrow(() -> new UndefinedExtension(extension))
            .contentType;
    }
}
