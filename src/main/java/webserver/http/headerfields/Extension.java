package webserver.http.headerfields;

import java.util.Optional;
import java.util.stream.Stream;

public enum Extension {
    CSS("css", HttpContentType.TEXT_CSS()),
    JS("js", HttpContentType.APPLICATION_JAVASCRIPT()),
    GIF("gif", HttpContentType.IMAGE_GIF()),
    JPG("jpg", HttpContentType.IMAGE_JPEG()),
    PNG("png", HttpContentType.IMAGE_PNG()),
    ICO("ico", HttpContentType.IMAGE_X_ICON()),
    TXT("txt", HttpContentType.TEXT_PLAIN());

    private final String extension;
    private final HttpContentType httpContentType;

    Extension(String extension, HttpContentType httpContentType) {
        this.extension = extension;
        this.httpContentType = httpContentType;
    }

    public static Optional<HttpContentType> extensionToContentType(String extension) {
        return Stream.of(values())
                .filter(subtype -> subtype.extension.equals(extension))
                .findAny()
                .map(result -> result.httpContentType);
    }
}