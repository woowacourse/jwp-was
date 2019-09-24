package http.common;

import java.util.Arrays;

public enum MimeType {
    html("text/html;charset=utf-8", "^.*\\.(html)$"),
    javascript("text/javascript;charset=utf-8", "^.*\\.(js)$"),
    css("text/css;charset=utf-8", "^.*\\.(css)$"),
    ttf("text/x-font-ttf", "^.*\\.(ttf)$"),
    woff("text/x-font-woff", "^.*\\.(woff)$"),
    ico("image/x-icon", "^.*\\.(ico)$"),
    png("image/png", "^.*\\.(png)$");

    private String contentType;
    private String regex;

    MimeType(String contentType, String regex) {
        this.contentType = contentType;
        this.regex = regex;
    }

    public static MimeType findByPath(String path) {
        return Arrays.stream(MimeType.values())
                .filter(mimeType -> path.matches(mimeType.regex))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getContentType() {
        return contentType;
    }
}
