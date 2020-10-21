package http.response;

public enum ContentType {
    HTML("text/html;charset=utf-8"),
    CSS("text/css; charset=utf-8"),
    FONT("font/*"),
    IMAGE("images/*"),
    JAVASCRIPT("application/javascript");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
