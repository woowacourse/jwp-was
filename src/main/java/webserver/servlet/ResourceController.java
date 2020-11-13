package webserver.servlet;

import webserver.controller.Controller;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestURI;
import webserver.http.response.HttpResponse;

public class ResourceController implements Controller {
    @Override
    public View doService(HttpRequest request, HttpResponse response) {
        RequestURI uri = request.getRequestLine().getRequestURI();
        return new FileView(uri);
    }
}
