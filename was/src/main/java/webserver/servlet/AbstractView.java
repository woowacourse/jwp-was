package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public abstract class AbstractView {
    abstract void render(HttpRequest request, HttpResponse response);
}
