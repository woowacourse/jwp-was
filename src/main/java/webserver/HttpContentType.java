package webserver;

import java.util.Arrays;
import java.util.function.Predicate;

public enum HttpContentType {
    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    JS(".js", "text/javascript");

    private final String extension;
    private final String contentType;

    HttpContentType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static String findContentType(String url) {
        return Arrays.stream(HttpContentType.values())
            .filter(isSameExtension(url))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("이상함! : " + url))
            .contentType;
    }

    public static boolean isHtmlFile(String uri) {
        return uri.endsWith(HTML.extension);
    }

    private static Predicate<HttpContentType> isSameExtension(String uri) {
        return type -> uri.endsWith(type.extension);
    }
}
