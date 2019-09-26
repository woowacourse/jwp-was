package webserver.http.response.core;

import webserver.http.request.core.RequestPath;

import java.util.Arrays;

public enum ResponseContentType {
    CSS("/css", "text/css;charset=utf-8"),
    JS("/js", "application/javascript;charset=utf-8"),
    FONTS("/fonts", "font/opentype;charset=utf-8"),
    IMAGES("/images", "image/jpeg;charset=utf-8"),
    UNDEFINED("undefined", "text/html;charset=utf-8");

    private String type;
    private String contentType;

    ResponseContentType(String type, String contentType) {
        this.type = type;
        this.contentType = contentType;
    }

    public static ResponseContentType of(RequestPath requestPath) {
        String path = requestPath.getPath();
        return Arrays.stream(values())
                .filter(value -> path.contains(value.type))
                .findAny()
                .orElse(UNDEFINED);
    }

    public String getContentType() {
        return contentType;
    }
}
