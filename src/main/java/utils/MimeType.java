package utils;

import java.util.Arrays;
import java.util.function.Predicate;

public enum MimeType {
    TEXT_HTML("text/html", value -> value.endsWith(".html")),
    TEXT_CSS("text/css", value -> value.endsWith(".css")),
    APPLICATION_OCTET_STREAM("application/octet-stream", value -> true);

    private final String mimeType;
    private final Predicate<String> predicate;

    MimeType(String mimeType, Predicate<String> predicate) {
        this.mimeType = mimeType;
        this.predicate = predicate;
    }

    public static MimeType from(String value) {
        return Arrays.stream(values())
            .filter(it -> it.predicate.test(value))
            .findFirst()
            .orElse(APPLICATION_OCTET_STREAM);
    }

    public String getMimeType() {
        return mimeType;
    }
}
