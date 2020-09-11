package utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StaticFileType {

    HTML("./templates", "text/html"),
    ICO("./templates", "image/icon"),
    CSS("./static", "text/css"),
    JS("./static", "Application/javascript"),
    PNG("./static", "image/jpeg"),
    TTF("./static", "application/font-ttf"),
    WOFF("./static", "application/font-woff");

    private final String path;
    private final String contentType;
}
