package webserver.controller.request;


import java.util.Arrays;

public enum MimeType {
    HTML(".html","text/html"),
    CSS(".css","text/css"),
    JS(".js","application/js"),
    ICO(".ico","image/x-icon"),
    TTF(".ttf","application/x-font-ttf"),
    WOFF(".woff","application/x-font-woff"),
    NONE("","");

    private String extension;
    private String mimeType;

    MimeType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public static MimeType match(String url) {
        return Arrays.stream(MimeType.values())
            .filter(m -> url.contains(m.extension))
            .findFirst()
            .orElse(MimeType.NONE);
    }

    public String getMimeType() {
        return mimeType;
    }
}
