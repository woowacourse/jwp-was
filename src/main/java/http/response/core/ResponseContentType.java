package http.response.core;

import http.request.core.RequestPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum ResponseContentType {
    CSS("/css", "Content-Type: text/css;charset=utf-8\r\n"),
    JS("/js", "Content-Type: application/javascript;charset=utf-8\r\n"),
    FONTS("/fonts", "Content-Type: font/opentype;charset=utf-8\r\n"),
    IMAGES("/images", "Content-Type: image/jpeg\r\n"),
    UNDEFINED("undefined", "Content-Type: text/html;charset=utf-8\r\n");

    private static final Logger log = LoggerFactory.getLogger(ResponseContentType.class);

    private String type;
    private String contentType;

    ResponseContentType(String type, String contentType) {
        this.type = type;
        this.contentType = contentType;
    }

    public static ResponseContentType of(RequestPath requestPath) {
        String path = requestPath.getFullPath();
        return Arrays.stream(values())
                .filter(value -> path.contains(value.type))
                .findAny()
                .orElse(UNDEFINED);
    }

    public String getContentType() {
        log.debug("ContentType : {}", contentType);
        return contentType;
    }
}
