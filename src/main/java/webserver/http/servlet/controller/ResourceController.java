package webserver.http.servlet.controller;

import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestLine;
import webserver.http.request.RequestURI;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.view.FileView;
import webserver.http.servlet.view.View;

public class ResourceController implements Controller {
    @Override
    public View doService(HttpRequest request, HttpResponse response) {
        HttpRequestLine requestLine = request.getRequestLine();
        RequestURI uri = requestLine.getRequestURI();
        return new FileView(uri);
    }
}
