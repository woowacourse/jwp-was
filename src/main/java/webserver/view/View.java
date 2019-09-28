package webserver.view;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

public interface View {
    void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse);
}
