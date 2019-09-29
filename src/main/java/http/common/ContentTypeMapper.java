package http.common;

import http.common.exception.InvalidContentTypeException;

import java.util.*;

import static utils.StringUtils.BLANK;

public enum ContentTypeMapper {
    JS(ContentType.JS, Arrays.asList("js")),
    CSS(ContentType.CSS, Arrays.asList("css")),
    HTML(ContentType.HTML, Arrays.asList("html")),
    PNG(ContentType.PNG, Arrays.asList("png")),
    EOT(ContentType.EOT, Arrays.asList("eot")),
    SVG(ContentType.SVG, Arrays.asList("svg")),
    TTF(ContentType.TTF, Arrays.asList("ttf")),
    WOFF(ContentType.WOFF, Arrays.asList("woff")),
    WOFF2(ContentType.WOFF2, Arrays.asList("woff2")),
    ICO(ContentType.ICO, Arrays.asList("ico")),
    FORM_URLENCODED(ContentType.FORM_URLENCODED, Collections.emptyList());

    private static Map<String, ContentTypeMapper> cache = new HashMap<>();
    private static final char DOT = '.';
    private static final int NOT_EXIST_EXTENSION_START_INDEX = 0;

    static {
        Arrays.stream(values())
                .filter(ContentTypeMapper::hasExtension)
                .forEach(contentType -> contentType.extensions.stream()
                        .forEach(ext -> cache.put(ext, contentType)));
    }

    private final String contentType;
    private final List<String> extensions;

    ContentTypeMapper(String contentType, List<String> extensions) {
        this.contentType = contentType;
        this.extensions = extensions;
    }

    private boolean hasExtension() {
        return extensions.size() > 0;
    }

    public static String getContentType(String path) {
        try {
            return cache.get(extractExtension(path)).contentType;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidContentTypeException();
        }
    }

    private static String extractExtension(String path) {
        int extensionStartIndex = path.lastIndexOf(DOT) + 1;
        return (extensionStartIndex == NOT_EXIST_EXTENSION_START_INDEX) ? BLANK : path.substring(extensionStartIndex);
    }
}
