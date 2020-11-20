package utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StaticFileType {

    HTML("text/html"),
    ICO("image/icon"),
    CSS("text/css"),
    JS("Application/javascript"),
    PNG("image/jpeg"),
    TTF("application/font-ttf"),
    WOFF("application/font-woff");

    private final String contentType;

    public static StaticFileType from(String type) {
        return StaticFileType.valueOf(type.toUpperCase());
    }
}
