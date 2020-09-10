package webserver;

import java.util.Arrays;
import java.util.function.Predicate;

public enum HttpContentType {
    HTML(".html", "text/html", false),
    CSS(".css", "text/css", true),
    JS(".js", "text/javascript", true);

    private final String extension;
    private final String contentType;
    private final boolean staticFile;

    HttpContentType(String extension, String contentType, boolean staticFile) {
        this.extension = extension;
        this.contentType = contentType;
        this.staticFile = staticFile;
    }

    public static String findContentType(String url) {
        return Arrays.stream(HttpContentType.values())
            .filter(isSameExtension(url))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당하는 확장자가 없습니다!"))
            .contentType;
    }

    public static boolean isStaticFile(String uri) {
        return Arrays.stream(HttpContentType.values())
            .filter(isSameExtension(uri))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당하는 확장자가 없습니다!"))
            .staticFile;
    }

    private static Predicate<HttpContentType> isSameExtension(String uri) {
        return type -> uri.endsWith(type.extension);
    }
}
