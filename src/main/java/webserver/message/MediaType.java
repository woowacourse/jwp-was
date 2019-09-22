package webserver.message;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum MediaType {
    TEXT_PLAIN("text/plain; charset=utf-8", "txt", "text"),
    TEXT_HTML("text/html; charset=utf-8", "html", "htm"),
    TEXT_CSS("text/css; charset=utf-8", "css"),
    TEXT_CSV("text/csv; charset=utf-8", "csv"),
    IMAGE_PNG("image/png", "png"),
    IMAGE_JPEG("image/jpeg", "jpg", "jpeg"),
    IMAGE_GIF("image/gif", "gif"),
    IMAGE_ICON("image/x-icon", "ico"),
    APPLICATION_JS("application/javascript; charset=utf-8", "js"),
    APPLICATION_JSON("application/json; charset=utf-8", "json"),
    APPLICATION_XML("application/xml; charset=utf-8", "xml"),
    APPLICATION_ZIP("application/zip", "zip"),
    APPLICATION_PDF("application/pdf", "pdf"),
    APPLICATION_BINARY("application/octet-stream", "*", "bin");

    private String mediaType;
    private List<String> extensions;

    MediaType(final String mediaType, final String... extensions) {
        this.mediaType = mediaType;
        this.extensions = Collections.unmodifiableList(Arrays.asList(extensions));
    }

    public static MediaType of(final String extension) {
        return Arrays.stream(values())
                .filter(m -> m.extensions.contains(extension.toLowerCase()))
                .findFirst()
                .orElse(MediaType.APPLICATION_BINARY);
    }

    public String getMediaType() {
        return mediaType;
    }

    public List<String> getExtensions() {
        return extensions;
    }
}
