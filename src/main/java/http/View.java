package http;

public class View {
    private HttpUri uri;

    public View(HttpUri uri) {
        this.uri = uri;
    }

    public String getResourceLocation() {
        return uri.getResourceLocation();
    }
}
