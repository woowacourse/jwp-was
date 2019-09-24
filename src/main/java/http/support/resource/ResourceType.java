package http.support.resource;

public interface ResourceType {
    String TEXT = "text/";
    String IMAGE = "image/";
    String FONT = "font/";

    String getResourceType();
}
