package resource;

public class Resource {

    private byte[] resource;
    private ContentType contentType;

    Resource(byte[] resource, ContentType contentType) {
        this.resource = resource;
        this.contentType = contentType;
    }

    public byte[] getResource() {
        return resource;
    }

    public ContentType getContentType() {
        return contentType;
    }
}
