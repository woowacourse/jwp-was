package webserver.resource;

enum ContentType {
    HTML("text/html"),
    CSS("text/css");

    private String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
