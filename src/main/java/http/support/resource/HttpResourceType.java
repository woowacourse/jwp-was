package http.support.resource;

public abstract class HttpResourceType implements ResourceType {
    private static final String DELIMITER_OF_EXTENSION = ".";

    protected final String extension;

    public HttpResourceType(String extension) {
        this.extension = extension;
    }
}
