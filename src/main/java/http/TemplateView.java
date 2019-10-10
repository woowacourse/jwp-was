package http;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class TemplateView implements View {
    private String path;

    public TemplateView(String path) {
        this.path = path;
    }

    @Override
    public void render(HttpRequest request, HttpResponse response) throws Exception {
        response.forward(request.getPath() + path);
    }
}
