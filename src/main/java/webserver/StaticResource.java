package webserver;

import java.util.Arrays;
import java.util.NoSuchElementException;

import http.response.ContentType;

public enum StaticResource {
    CSS("/css", ContentType.CSS.getContentType()),
    FONTS("/fonts", ContentType.FONT.getContentType()),
    IMAGES("/images", ContentType.IMAGE.getContentType()),
    JS("/js", ContentType.JAVASCRIPT.getContentType());

    public static final String STATIC_PATH = "./static";

    private final String resourcePath;
    private final String contentType;

    StaticResource(String resourcePath, String contentType) {
        this.resourcePath = resourcePath;
        this.contentType = contentType;
    }

    public static StaticResource fromPath(String path) {
        if (path == null) {
            throw new NullPointerException("RequestPath가 존재하지 않습니다.");
        }

        return Arrays.stream(values())
                .filter(staticResource -> path.startsWith(staticResource.resourcePath))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    public static boolean match(String path) {
        if (path == null) {
            throw new NullPointerException("RequestPath가 존재하지 않습니다.");
        }

        return Arrays.stream(values())
                .anyMatch(staticResource -> path.startsWith(staticResource.resourcePath));
    }

    public String getContentType() {
        return contentType;
    }
}
