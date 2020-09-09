package utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StaticFileType {

    HTML("./templates", "text/html"),
    ICO("./templates", "image/x-icon"),
    CSS("./static", "text/css"),
    JS("./static", "Application/x-javascript"),
    PNG("./static", "image/jpeg"),
    TTF("./static", "application/x-font-ttf"),
    WOFF("./static", "application/x-font-woff");

    private final String path;
    private final String contentType;
}
