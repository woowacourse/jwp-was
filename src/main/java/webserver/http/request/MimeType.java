package webserver.http.request;

import java.util.Arrays;

import webserver.exception.UnsupportedMimeTypeException;

public enum MimeType {

    HTML_UTF_8("./templates", ".html", "text/html;charset=utf-8"),
    ICO("./templates", ".ico", "image/x-icon"),
    CSS_UTF_8("./static", ".css", "text/css;charset=utf-8"),
    JS_UTF_8("./static", ".js", "application/js;charset=utf-8"),
    PNG("./static", ".png", "image/png"),
    EOT("./static", ".eot", "application/x-font-eot"),
    SVG("./static", ".svg", "image/svg+xml"),
    WOFF("./static", ".woff", "application/x-font-woff"),
    WOFF2("./static", ".woff2", "application/x-font-woff"),
    TTF("./static", ".ttf", "application/x-font-ttf");

    private final String filePosition;
    private final String extensionType;
    private final String mimeType;

    MimeType(String filePosition, String extensionType, String mimeType) {
        this.filePosition = filePosition;
        this.extensionType = extensionType;
        this.mimeType = mimeType;
    }

    public static MimeType of(String path) {
        String targetExtensionType = path.substring(path.lastIndexOf("."));
        return Arrays.stream(values())
                .filter(mime -> mime.extensionType.equalsIgnoreCase(targetExtensionType))
                .findAny()
                .orElseThrow(() -> new UnsupportedMimeTypeException(targetExtensionType));
    }

    public String getFilePosition() {
        return filePosition;
    }

    public String getMimeType() {
        return mimeType;
    }
}
