package view;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.Map;

public interface View {
    void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse);
}
