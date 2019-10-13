package view;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.Map;

public class TemplateView implements View {
    private String path;

    public TemplateView(String path) {
        this.path = path;
    }

    @Override
    public void render(Map<String, ?> model, HttpRequest request, HttpResponse response) throws Exception {
        response.forward(request.getPath() + path);
    }
}
