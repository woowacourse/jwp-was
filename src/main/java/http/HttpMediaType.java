package http;

public enum HttpMediaType {
    HTML("text/html"), CSS("text/css");

    public static final HttpMediaType DEFAULT_MEDIA_TYPE = HTML;

    private String mediaType;

    HttpMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String toString() {
        return mediaType;
    }
}
