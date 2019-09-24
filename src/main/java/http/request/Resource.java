package http.request;

import http.MediaType;

import java.util.Objects;

public class Resource {
    private String path;
    private String fileName;
    private MediaType mediaType;

    public Resource(String path, String fileName, MediaType mediaType) {
        this.path = path;
        this.fileName = fileName;
        this.mediaType = mediaType;
    }

    public String getUri() {
        return path + fileName + mediaType.getExtensionPath();
    }

    @Override
    public String toString() {
        return "Resource{" +
                "path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                ", mediaType=" + mediaType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return path.equals(resource.path) &&
                fileName.equals(resource.fileName) &&
                mediaType == resource.mediaType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, fileName, mediaType);
    }
}
