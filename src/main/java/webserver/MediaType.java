package webserver;

import java.util.Arrays;
import java.util.Optional;

public enum MediaType {
    HTML("text/html", "html"),
    JS("application/javascript", "js"),
    CSS("text/css", "css"),
    PNG("image/png", "png"),
    EOT("application/vnd.ms-fontobject", "eot"),
    SVG("image/svg+xml", "svg"),
    TTF("application/font-sfnt", "ttf"),
    WOFF("application/font-woff", "woff"),
    WOFF2("font/woff2", "woff2");

    private String headerValue;
    private String extension;

    MediaType(String headerValue, String extension) {
        this.headerValue = headerValue;
        this.extension = extension;
    }

    public static Optional<MediaType> fromExtension(String extension) {
        return Arrays.stream(values())
            .filter(mediaType -> mediaType.extension.equals(extension.toLowerCase()))
            .findFirst();
    }

    public String getHeaderValue() {
        return headerValue;
    }
}
