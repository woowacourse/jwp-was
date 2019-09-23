package webserver.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum MimeType {

    CSS("css", "text/css;charset=utf-8"),
    JS("js", "text/javascript;charset=UTF-8:"),
    HTM("htm", "text/html;charset=utf-8"),
    HTML("html", "text/html;charset=utf-8"),
    GIF("gif", "image/gif"),
    PNG("png", "image/png"),
    JPG("jpg", "image/jpeg"),
    JPEG("jpeg", "image/jpeg"),
    BMP("bmp", "image/bmp"),
    WEPB("webp", "image/webp"),
    TTF("ttf", "font/ttf"),
    WOFF("woff", "font/woff"),
    ICO("ico", "image/vnd.microsoft.icon");

    public final static Map<String, String> mimeTypes = new HashMap<>();

    static {
        Arrays.stream(values())
                .forEach(mimeType -> mimeTypes.put(mimeType.extensions, mimeType.type));
    }

    private final String extensions;
    private final String type;

    MimeType(final String extensions, final String type) {
        this.extensions = extensions;
        this.type = type;
    }

    public static String getType(String extension) {
        return mimeTypes.get(extension);
    }

    public static boolean contains(final String extension) {
        return mimeTypes.containsKey(extension);
    }

    public static Set<String> getExtensions() {
        return mimeTypes.keySet();
    }
}
