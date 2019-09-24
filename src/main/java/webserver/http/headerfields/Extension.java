package webserver.http.headerfields;

import java.util.Optional;
import java.util.stream.Stream;

public enum Extension {
    CSS("css", HttpContentType.getHttpContentType("text/css")),
    JS("js", HttpContentType.getHttpContentType("application/javascript")),
    GIF("gif", HttpContentType.getHttpContentType("image/gif")),
    JPG("jpg", HttpContentType.getHttpContentType("image/jpeg")),
    PNG("png", HttpContentType.getHttpContentType("image/png")),
    ICO("ico", HttpContentType.getHttpContentType("image/x-icon")),
    TXT("txt", HttpContentType.getHttpContentType("text/plain"));

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