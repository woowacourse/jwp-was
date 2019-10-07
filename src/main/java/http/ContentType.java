package http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ContentType {
    HTML("text/html", "html"),
    CSS("text/css", "css"),
    JS("application/javascript", "js"),
    PNG("image/png", "png"),
    JPG("image/jpeg", "jpg"),
    TXT("text/plain", "txt"),
    XML("text/xml", "xml"),
    JSON("application/json", "json"),
    RSS("application/rss+xml", "rss"),
    MAP("application/json", "map"),
    MJS("text/javascript", "mjs"),
    OCTET_STREAM("application/octet-stream", ""),
    // Manifest files
    WEBMANIFEST("application/manifest+json", "webmanifest"),
    APPCACHE("text/cache-manifest", "appcache"),
    // Media files
    F4A("audio/mp4", "f4a"),
    F4B("audio/mp4", "f4b"),
    M4A("audio/mp4", "m4a"),
    OGA("audio/ogg", "oga"),
    OGG("audio/ogg", "ogg"),
    SPX("audio/ogg", "spx"),
    MP4("video/mp4", "mp4"),
    MP4V("video/mp4", "mp4v"),
    MPG4("video/mp4", "mpg4"),
    OGV("video/ogg", "ogv"),
    WEBM("video/webm", "webm"),
    FLV("video/x-flv", "flv"),
    SVGZ("image/svg+xml", "svgz"),
    CUR("image/x-icon", "cur"),
    WEBP("image/webp", "webp"),
    // Web fonts
    EOT("application/vnd.ms-fontobject", "eot"),
    WOFF("font/woff", "woff"),
    WOFF2("font/woff2", "woff2"),
    TTF("font/ttf", "ttf"),
    TTC("font/collection", "ttc"),
    OTF("font/otf", "otf"),
    // Other
    VTT("text/vtt", "vtt"),

    ALL("*/*", "*/*");

    private static final String DEFAULT_CHAR_SET = "utf-8";

    private static final Map<String, ContentType> contentTypesByMimeType = new HashMap<String, ContentType>() {{
        for (ContentType type : ContentType.class.getEnumConstants()) {
            put(type.mimeType, type);
        }
    }};

    private static final Map<String, ContentType> contentTypesByExtension = new HashMap<String, ContentType>() {{
        for (ContentType type : ContentType.class.getEnumConstants()) {
            put(type.extension, type);
        }
    }};

    private final String mimeType;
    private final String extension;

    ContentType(String mimeType, String extension) {
        this.mimeType = mimeType;
        this.extension = extension;
    }

    public static Optional<ContentType> fromMimeType(String mimeType) {
        return Optional.ofNullable(contentTypesByMimeType.get(mimeType));
    }

    public static Optional<ContentType> fromExtension(String extension) {
        return Optional.ofNullable(contentTypesByExtension.get(extension));
    }

    public static boolean isSupportedMimeType(String mimeType) {
        return contentTypesByMimeType.containsKey(mimeType);
    }

    public boolean canAccept(ContentType contentType) {
        if (ContentType.ALL.equals(this)) {
            return true;
        }
        return this.equals(contentType);
    }

    public String toHeaderValue() {
        return String.format("%s;charset=%s", mimeType, DEFAULT_CHAR_SET);
    }
}
