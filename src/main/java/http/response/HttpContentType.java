package http.response;

import http.response.exception.InvalidContentTypeException;

import java.util.Arrays;
import java.util.function.Predicate;

public enum HttpContentType {
    HTML("text/html", url -> url.matches(".*\\.html$")),
    JS("text/javascript", url -> url.matches(".*\\.js$")),
    CSS("text/css", url -> url.matches(".*\\.css$")),
    IMG("image/*", url -> url.matches(".*\\.(png|jpg|jpeg|ico)$")),
    FONT("application/*; image/*", url -> url.matches(".*\\.(svg|eot|ttf|woff|woff2)"));

    private final String contentType;
    private final Predicate<String> contentTypeChecker;

    HttpContentType(final String contentType, final Predicate<String> contentTypeChecker) {
        this.contentType = contentType;
        this.contentTypeChecker = contentTypeChecker;
    }

    public static String findContentType(final String url) {
        return Arrays.stream(HttpContentType.values())
                .filter(contentType -> contentType.contentTypeChecker.test(url))
                .findFirst()
                .orElseThrow(InvalidContentTypeException::new).contentType;
    }
}
