package webserver.http.request;

import java.util.Arrays;

public enum TemplateFile {
    HTML(".html", "text/html");

    private final String suffix;
    private final String contentType;

    TemplateFile(String suffix, String contentType) {
        this.suffix = suffix;
        this.contentType = contentType;
    }

    public static boolean isTemplate(String uri) {
        return Arrays.stream(values())
                .anyMatch(value -> uri.endsWith(value.suffix));
    }

    public static TemplateFile of(String uri) {
        return Arrays.stream(values())
                .filter(value -> uri.endsWith(value.suffix))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 파일입니다. : " + uri));
    }

    public String getSuffix() {
        return suffix;
    }

    public String getContentType() {
        return contentType;
    }
}
