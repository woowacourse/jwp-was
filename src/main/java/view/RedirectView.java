package view;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.Map;

public class RedirectView implements View {
    private String path;

    public RedirectView(String path) {
        this.path = path;
    }

    @Override
    public void render(Map<String, ?> model, HttpRequest request, HttpResponse response) {
        response.redirect(path);
    }
}
