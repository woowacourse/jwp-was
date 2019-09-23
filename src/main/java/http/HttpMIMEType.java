package http;

import java.util.Arrays;

public enum HttpMIMEType {
    PLAIN("text/plain", "txt"),
    HTML("text/html", "html"),
    CSS("text/css", "css"),
    JS("text/javascript", "js"),
    JPEG("image/jpeg", "jpeg"),
    PNG("image/png", "png"),
    WEBP("image/webp", "webp"),
    APNG("image/apng", "apng"),
    MP4("video/mp4", "mp4"),
    XML("application/xml", "xml"),
    WOFF("font/woff", "woff"),
    TTF("font/ttf", "ttf"),
    OTF("font/otf", "otf");

    public static final HttpMIMEType DEFAULT_MEDIA_TYPE = PLAIN;
    private static final String ALL_TYPE = "*/*";

    private String mimeType;
    private String extension;

    HttpMIMEType(String mimeType, String extension) {
        this.mimeType = mimeType;
        this.extension = extension;
    }

    public static HttpMIMEType getMIMETypeFrom(String accept, String extension) {
        String bestVariant = extractBestVariantFrom(accept);
        if (ALL_TYPE.equals(bestVariant)) {
            return resolveWithExtension(extension);
        }
        return resolveWithMIMEType(bestVariant);
    }

    private static String extractBestVariantFrom(String accept) {
        String[] variants = accept.split(",");
        if (variants.length > 0) {
            return extractMIMEType(variants[0]);
        }
        return DEFAULT_MEDIA_TYPE.mimeType;
    }

    private static String extractMIMEType(String variant) {
        String[] splicedVariant = variant.split(";");
        if (splicedVariant.length > 0) {
            return splicedVariant[0];
        }
        return DEFAULT_MEDIA_TYPE.mimeType;
    }

    private static HttpMIMEType resolveWithMIMEType(String type) {
        return Arrays.stream(HttpMIMEType.values())
                .filter(mimeType -> mimeType.mimeType.equals(type))
                .findFirst()
                .orElse(PLAIN);
    }

    private static HttpMIMEType resolveWithExtension(String extension) {
        return Arrays.stream(HttpMIMEType.values())
                .filter(mimeType -> mimeType.extension.equals(extension))
                .findFirst()
                .orElse(PLAIN);
    }

    @Override
    public String toString() {
        return mimeType;
    }
}
