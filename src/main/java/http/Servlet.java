package http;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Servlet {
    void service(HttpRequest httpRequest, HttpResponse response);
}
