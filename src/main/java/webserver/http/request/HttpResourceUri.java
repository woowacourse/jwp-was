package webserver.http.request;

import java.util.Objects;

public class HttpResourceUri {
    private static final String FILE_EXTENSION_DELIMITER = ".";

    private final String resourceUri;
    private final HttpResourceType httpResourceType;

    private HttpResourceUri(String resourceUri, HttpResourceType httpResourceType) {
        this.resourceUri = resourceUri;
        this.httpResourceType = httpResourceType;
    }

    public static HttpResourceUri from(String resourceUri) {
        Objects.requireNonNull(resourceUri);

        int fileExtensionIndex = resourceUri.lastIndexOf(FILE_EXTENSION_DELIMITER);
        if (fileExtensionIndex == -1) {
            return new HttpResourceUri(resourceUri, HttpResourceType.NONE);
        }

        String fileExtension = resourceUri.substring(fileExtensionIndex + 1).trim();

        return new HttpResourceUri(resourceUri, HttpResourceType.from(fileExtension));
    }

    public byte[] readFile() {
        return this.httpResourceType.readFile(this.resourceUri);
    }

    public String getContentType() {
        return this.httpResourceType.getContentType();
    }

    public String toHttpMessage() {
        return this.resourceUri;
    }
}
