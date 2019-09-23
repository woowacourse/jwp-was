package webserver.domain;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum MediaType {
    TEXT_PLAIN("text/plain", StandardCharsets.UTF_8, "txt", "text"),
    TEXT_HTML("text/html", StandardCharsets.UTF_8, "html", "htm"),
    TEXT_CSS("text/css", StandardCharsets.UTF_8, "css"),
    TEXT_CSV("text/csv", StandardCharsets.UTF_8, "csv"),
    IMAGE_PNG("image/png", "png"),
    IMAGE_JPEG("image/jpeg", "jpg", "jpeg"),
    IMAGE_GIF("image/gif", "gif"),
    IMAGE_ICON("image/x-icon", "ico"),
    APPLICATION_JS("application/javascript", StandardCharsets.UTF_8, "js"),
    APPLICATION_JSON("application/json", StandardCharsets.UTF_8, "json"),
    APPLICATION_XML("application/xml", StandardCharsets.UTF_8, "xml"),
    APPLICATION_ZIP("application/zip", "zip"),
    APPLICATION_PDF("application/pdf", "pdf"),
    APPLICATION_BINARY("application/octet-stream", "*", "bin");

    public static final String EMPTY = "";
    public static final String CHARSET = "; charset=";
    private final String mediaType;
    private final Charset charset;
    private final List<String> extensions;
    private final String message;

    MediaType(final String mediaType, final Charset charset, final String... extensions) {
        this.mediaType = mediaType.toLowerCase();
        this.charset = charset;
        this.extensions = Arrays.asList(extensions);
        this.message = this.mediaType + getCharsetString(this.charset);
    }

    MediaType(final String mediaType, final String... extensions) {
        this(mediaType, null, extensions);
    }

    public static MediaType of(final String extension) {
        return Arrays.stream(values())
                .filter(m -> m.extensions.contains(extension.toLowerCase()))
                .findFirst()
                .orElse(MediaType.APPLICATION_BINARY);
    }

    public String is() {
        return message;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    private String getCharsetString(final Charset charset) {
        return Objects.isNull(charset) ? EMPTY : CHARSET + charset.name().toLowerCase();
    }
}
