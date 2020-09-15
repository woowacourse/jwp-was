package web;

import java.util.Arrays;

public enum StaticFile {

    HTML(".html", "./templates", "text/html"),
    ICO(".ico", "./templates", "image/vnd.microsoft.icon"),
    CSS(".css", "./static", "text/css"),
    JS(".js", "./static", "text/javascript"),
    WOFF(".woff", "./static", "text/woff"),
    PNG(".png", "./static", "image/png"),
    JPEG(".jpeg", "./static", "image/jpeg"),
    SVG(".svg", "./static", "image/svg_xml");

    private final String suffix;
    private final String prefix;
    private final String type;

    StaticFile(String suffix, String prefix, String type) {
        this.suffix = suffix;
        this.prefix = prefix;
        this.type = type;
    }

    public static StaticFile of(String path) {
        return Arrays.stream(values())
            .filter(v -> path.endsWith(v.suffix))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    public String getSuffix() {
        return suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getType() {
        return type;
    }
}
