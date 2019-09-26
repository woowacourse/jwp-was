package servlet;

import http.request.HttpRequest;
import http.response.HttpResponseEntity;

import java.net.URISyntaxException;

public interface Servlet {
    HttpResponseEntity handle(HttpRequest httpRequest) throws URISyntaxException;
}
