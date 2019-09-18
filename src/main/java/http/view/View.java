package http.view;

import http.model.HttpUri;

public class View {
    private String uri;

    public View(String  uri) {
        this.uri = uri;
    }

    public String getResourceLocation() {
        return uri;
    }
}
