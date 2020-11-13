package webserver.servlet;

import webserver.servlet.http.request.HttpRequest;
import webserver.servlet.http.response.HttpResponse;

public abstract class GenericServlet {

    public abstract void doService(HttpRequest request, HttpResponse response);
}
