package http;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class RedirectView implements View {
    private String path;

    public RedirectView(String path) {
        this.path = path;
    }

    @Override
    public void render( HttpRequest request, HttpResponse response) {
        response.redirect(path);
    }
}
