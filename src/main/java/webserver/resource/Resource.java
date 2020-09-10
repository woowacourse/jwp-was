package webserver.resource;

public class Resource {

    private byte[] resource;
    private ContentType contentType;

    public Resource(byte[] resource, ContentType contentType) {
        this.resource = resource;
        this.contentType = contentType;
    }

    public byte[] getResource() {
        return resource;
    }

    public String getContentType() {
        return contentType.getContentType();
    }
}
