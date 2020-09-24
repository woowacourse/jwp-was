package webserver.response;

public class FileResponse {
    private final String filePath;
    private final String contentType;

    public FileResponse(String filePath, String contentType) {
        this.filePath = filePath;
        this.contentType = contentType;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getContentType() {
        return contentType;
    }
}
