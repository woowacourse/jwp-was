package webserver.http;

import exception.FileNotReadableException;
import exception.HttpResourceTypeNotFoundException;
import utils.FileIoUtils;

import java.util.Arrays;
import java.util.Objects;

public enum HttpResourceType {
    NONE("", "", ""),
    HTML("html", "./templates", "text/html"),
    CSS("css", "./static", "text/css");

    private final String extension;
    private final String path;
    private final String contentType;

    HttpResourceType(String extension, String path, String contentType) {
        this.extension = extension;
        this.path = path;
        this.contentType = contentType;
    }

    public static HttpResourceType from(String fileExtension) {
        Objects.requireNonNull(fileExtension);

        return Arrays.stream(HttpResourceType.values())
                .filter(httpResourceType -> httpResourceType.extension.equals(fileExtension))
                .findFirst()
                .orElseThrow(() -> new HttpResourceTypeNotFoundException(fileExtension));
    }

    public byte[] readFile(String uri) {
        String filePath = this.path + uri;

        try {
            return FileIoUtils.loadFileFromClasspath(filePath);
        } catch (Exception e) {
            throw new FileNotReadableException(filePath);
        }
    }

    public String getContentType() {
        return this.contentType;
    }
}
