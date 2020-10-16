package web;

import java.util.Arrays;
import java.util.Map;

public enum StaticFile {

    HTML("./templates", "text/html"),
    ICO("./templates", "image/vnd.microsoft.icon"),
    CSS("./static", "text/css"),
    JS("./static", "text/javascript"),
    WOFF("./static", "text/woff"),
    PNG("./static", "image/png"),
    JPEG("./static", "image/jpeg"),
    SVG("./static", "image/svg_xml");

    private final String prefix;
    private final String type;

    StaticFile(String prefix, String type) {
        this.prefix = prefix;
        this.type = type;
    }

    public static StaticFile of(String path) {
        return Arrays.stream(values())
            .filter(v -> path.endsWith(v.name().toLowerCase()))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    public static Map<String, String> writeContentType(Map<String, String> headers, String url) {
        Arrays.stream(values())
            .filter(v -> url.endsWith(v.name().toLowerCase()))
            .findFirst()
            .map(v -> headers.put("Content-Type", v.type));

        return headers;
    }

    public boolean isContentType(HttpRequest request) {
        return type.equals(request.getHeaders().get("Content-Type"));
    }

    public String getPrefix() {
        return prefix;
    }

    public String getType() {
        return type;
    }
}
