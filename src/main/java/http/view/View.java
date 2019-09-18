package http.view;

public class View {
    private String uri;

    public View(String uri) {
        this.uri = uri;
    }

    public String getResourceLocation() {
        return uri;
    }
}
