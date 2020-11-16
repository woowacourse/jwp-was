package webserver.http.servlet.controller;

import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestLine;
import webserver.http.request.RequestURI;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractView;
import webserver.http.servlet.FileView;

public class ResourceController implements Controller {
    @Override
    public AbstractView doService(HttpRequest request, HttpResponse response) {
        HttpRequestLine requestLine = request.getRequestLine();
        RequestURI uri = requestLine.getRequestURI();
        return new FileView(uri);
    }
}
