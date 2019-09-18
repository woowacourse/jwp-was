package http.view;

import http.model.HttpUri;

public class View {
    private HttpUri uri;

    public View(HttpUri uri) {
        this.uri = uri;
    }

    public String getResourceLocation() {
        return uri.getResourceLocation();
    }
}
