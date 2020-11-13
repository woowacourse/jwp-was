package webserver.http.servlet.view;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface View {
    void render(HttpRequest request, HttpResponse response);
}
