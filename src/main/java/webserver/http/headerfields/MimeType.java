package webserver.http.headerfields;

public enum MimeType {
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    APPLICATION_JSON("application/json"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    TEXT_HTML("text/html"),
    TEXT_CSS("text/css"),
    TEXT_PLAIN("text/plain"),
    APPLICATION_JAVASCRIPT("application/javascript"),
    IMAGE_GIF("image/gif"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_X_ICON("image/x-icon");

    private final String mimeType;

    MimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return mimeType;
    }
}