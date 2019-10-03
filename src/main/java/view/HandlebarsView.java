package view;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class HandlebarsView implements View {
    private String viewName;

    public HandlebarsView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            httpResponse.ok(HandlebarsViewRenderer.getBody(viewName, model));
        } catch (IOException e) {
            httpResponse.sendInternalServerError();
        }
    }
}
