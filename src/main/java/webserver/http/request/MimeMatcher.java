package webserver.http.request;

import java.util.Arrays;

public enum MimeMatcher {
    HTML("./templates", ".html", "text/html"),
    ICO("./templates", ".ico", "image/x-icon"),
    CSS("./static", ".css", "text/css"),
    JS("./static", ".js", "application/js"),
    PNG("./static", ".png", "image/png"),
    EOT("./static", ".eot", "application/x-font-eot"),
    SVG("./static", ".svg", "image/svg+xml"),
    WOFF("./static", ".woff", "application/x-font-woff"),
    WOFF2("./static", ".woff2", "application/x-font-woff"),
    TTF("./static", ".ttf", "application/x-font-ttf");

    private final String filePosition;
    private final String extensionType;
    private final String mimeType;

    MimeMatcher(String filePosition, String extensionType, String mimeType) {
        this.filePosition = filePosition;
        this.extensionType = extensionType;
        this.mimeType = mimeType;
    }

    public static MimeMatcher of(String path) {
        final String targetExtensionType = path.substring(path.lastIndexOf("."));
        return Arrays.stream(values())
                .filter(mime -> mime.extensionType.equalsIgnoreCase(targetExtensionType))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getFilePosition() {
        return filePosition;
    }

    public String getMimeType() {
        return mimeType;
    }
}
