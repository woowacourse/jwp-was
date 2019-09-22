package http;

import java.util.Arrays;

public enum MimeType {
    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    SVG(".svg", "image/svg+xml"),
    TTF(".ttf", "application/x-font-ttf"),
    WOFF(".woff", "application/x-font-woff"),
    PNG(".png", "image/png"),
    JS(".js", "application/js"),
    OTHER("***", "application/octet-stream");

    private String extension;
    private String mimeType;

    MimeType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static MimeType of(String extension) {
        return Arrays.stream(values())
                .filter(mimeType -> mimeType.extension.equals(extension))
                .findFirst()
                .orElse(MimeType.OTHER);
    }

    public boolean isHtml() {
        return this.equals(HTML);
    }
}
