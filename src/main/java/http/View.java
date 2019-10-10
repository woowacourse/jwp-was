package http;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface View {
    void render(HttpRequest request, HttpResponse response) throws Exception;
}
