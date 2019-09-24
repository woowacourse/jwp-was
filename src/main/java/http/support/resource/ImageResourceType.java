package http.support.resource;

public class ImageResourceType extends HttpResourceType {
    public ImageResourceType(String extension) {
        super(extension);
    }

    @Override
    public String getResourceType() {
        return IMAGE + this.extension;
    }
}
