package webserver.resource;

enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("application/javascript"),
    ICON("image/x-icon"),
    FONT_TTF("font/ttf"),
    FONT_WOFF("font/woff");

    private String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
