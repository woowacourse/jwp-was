package webserver.controller;

import java.util.Optional;

public enum ContentType {
    HTML("text/html", Optional.of("charset=utf-8"), Optional.empty()),
    CSS("text/css", Optional.empty(), Optional.empty()),
    JAVASCRIPT("application/javascript", Optional.empty(), Optional.empty()),
    TEXT("text/plain", Optional.empty(), Optional.empty());

    private final String mediaType;
    private final Optional<String> charset;
    private final Optional<String> boundary;

    ContentType(String mediaType, Optional<String> charset, Optional<String> boundary) {
        this.mediaType = mediaType;
        this.charset = charset;
        this.boundary = boundary;
    }

    public String value() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(mediaType);
        charset.ifPresent(charset -> stringBuilder.append(String.format(";%s", charset)));
        boundary.ifPresent(boundary -> stringBuilder.append(String.format(";%s", boundary)));

        return stringBuilder.toString();
    }
}
