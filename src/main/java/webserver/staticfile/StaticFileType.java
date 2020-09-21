package webserver.staticfile;

public enum StaticFileType {
    HTML("html", "./templates"),
    JS("js", "./static"),
    CSS("css", "./static");

    private final String fileType;
    private final String prePath;

    StaticFileType(String fileType, String prePath) {
        this.fileType = fileType;
        this.prePath = prePath;
    }

    public String getPrePath() {
        return prePath;
    }

    public String getFileType() {
        return fileType;
    }
}

