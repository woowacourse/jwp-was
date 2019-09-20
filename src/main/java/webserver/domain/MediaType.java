package webserver.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum MediaType {
    TEXT_PLAIN("text/plain", "txt", "text"),
    TEXT_HTML("text/html", "html", "htm"),
    TEXT_CSS("text/css", "css"),
    TEXT_CSV("text/csv", "csv"),
    IMAGE_PNG("image/png", "png"),
    IMAGE_JPEG("image/jpeg", "jpg", "jpeg"),
    IMAGE_GIF("image/gif", "gif"),
    APPLICATION_JS("application/javascript", "js"),
    APPLICATION_JSON("application/json", "json"),
    APPLICATION_XML("application/xml", "xml"),
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
                .orElseGet(() -> MediaType.APPLICATION_BINARY);
    }

    public String getMediaType() {
        return mediaType;
    }

    public List<String> getExtensions() {
        return extensions;
    }
}
