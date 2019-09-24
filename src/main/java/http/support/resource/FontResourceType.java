package http.support.resource;

public class FontResourceType extends HttpResourceType {
    public FontResourceType(String extension) {
        super(extension);
    }

    @Override
    public String getResourceType() {
        return FONT + extension;
    }
}
