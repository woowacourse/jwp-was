package webserver.resource;

enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("application/javascript");

    private String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
