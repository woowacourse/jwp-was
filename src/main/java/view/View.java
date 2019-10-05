package view;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface View {
    void render(HttpRequest httpRequest, HttpResponse httpResponse);

    String getViewName();
}
