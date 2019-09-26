package servlet;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Servlet {
    void handle(HttpRequest request, HttpResponse response) throws URISyntaxException, IOException;
}
