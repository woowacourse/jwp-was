package http.support.resource;

public class TextResourceType extends HttpResourceType {
    public TextResourceType(String extension) {
        super(extension);
    }

    @Override
    public String getResourceType() {
        return TEXT + this.extension;
    }
}
