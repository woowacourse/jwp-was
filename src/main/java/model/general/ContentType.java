package model.general;

import java.util.Arrays;
import java.util.Optional;
import model.request.HttpRequest;

public enum ContentType {

    HTML("text/html", ".html"),
    CSS("text/css", ".css"),
    JS("text/javascript", ".js"),
    WOFF("text/font", ".woff"),
    TTF("text/font", ".ttf"),
    ICO("image/x-icon", ".ico"),
    PNG("image/png", ".png");

    private final String contentTypeValue;
    private final String extension;

    ContentType(String contentTypeValue, String extension) {
        this.contentTypeValue = contentTypeValue;
        this.extension = extension;
    }

    public static Optional<ContentType> of(String extension) {
        return Arrays.stream(values())
            .filter(c -> c.extension.equals(extension))
            .findFirst();
    }

    public static Optional<ContentType> of(HttpRequest httpRequest) {
        String extension = httpRequest.extractRequestUriExtension();

        return of(extension);
    }

    public String getContentTypeValue() {
        return contentTypeValue;
    }
}
