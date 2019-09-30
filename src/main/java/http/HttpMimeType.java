package http;

import java.util.Arrays;

public enum HttpMimeType {
    X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded", ""),
    XML("application/xml", "xml"),
    PLAIN("text/plain", "txt"),
    HTML("text/html", "html"),
    CSS("text/css", "css"),
    JS("text/javascript", "js"),
    JPEG("image/jpeg", "jpeg"),
    PNG("image/png", "png"),
    WEBP("image/webp", "webp"),
    APNG("image/apng", "apng"),
    MP4("video/mp4", "mp4"),
    WOFF("font/woff", "woff"),
    TTF("font/ttf", "ttf"),
    OTF("font/otf", "otf");

    public static final HttpMimeType DEFAULT_MEDIA_TYPE = PLAIN;

    private static final String ALL_TYPE = "*/*";
    private static final String ACCEPT_DELIMITER = ",";
    private static final String VARIANT_DELIMITER = ";";

    private String mimeType;
    private String extension;

    HttpMimeType(String mimeType, String extension) {
        this.mimeType = mimeType;
        this.extension = extension;
    }

    public static HttpMimeType getMimeTypeFrom(String accept, String extension) {
        String bestVariant = extractBestVariantFrom(accept);
        if (ALL_TYPE.equals(bestVariant)) {
            return resolveWithExtension(extension);
        }
        return resolveWithMimeType(bestVariant);
    }

    private static String extractBestVariantFrom(String accept) {
        String[] variants = accept.split(ACCEPT_DELIMITER);
        if (variants.length > 0) {
            return extractMimeType(variants[0]);
        }
        return DEFAULT_MEDIA_TYPE.mimeType;
    }

    private static String extractMimeType(String variant) {
        String[] splicedVariant = variant.split(VARIANT_DELIMITER);
        if (splicedVariant.length > 0) {
            return splicedVariant[0];
        }
        return DEFAULT_MEDIA_TYPE.mimeType;
    }

    private static HttpMimeType resolveWithMimeType(String type) {
        return Arrays.stream(HttpMimeType.values())
                .filter(mimeType -> mimeType.mimeType.equals(type))
                .findFirst()
                .orElse(PLAIN);
    }

    private static HttpMimeType resolveWithExtension(String extension) {
        return Arrays.stream(HttpMimeType.values())
                .filter(mimeType -> mimeType.extension.equals(extension))
                .findFirst()
                .orElse(PLAIN);
    }

    public boolean match(String mimeType) {
        return this.mimeType.equals(mimeType);
    }

    @Override
    public String toString() {
        return mimeType;
    }
}