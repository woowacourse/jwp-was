package utils;

import java.util.Arrays;

public enum ContentTypeMatcher {
    HTML(".html", "text/html;charset=utf-8"),
    JAVASCRIPT(".js", "application/javascript;charset=utf-8"),
    CSS(".css", "text/css;charset=utf-8"),
    FONT_WOFF(".woff", ""),
    ICON(".ico", "");

    private final String suffix;
    private final String contentType;

    ContentTypeMatcher(final String suffix, final String contentType) {
        this.suffix = suffix;
        this.contentType = contentType;
    }

    public static String findResourceContentType(String requestPath) {
        return Arrays.stream(values())
                .filter(resourceMatcher -> requestPath.endsWith(resourceMatcher.suffix))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s :일치하는 확장자가 존재하지 않습니다.", requestPath)))
                .contentType;
    }

}