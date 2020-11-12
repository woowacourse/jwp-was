package webserver;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public abstract class GenericServlet {

    public abstract void doService(HttpRequest request, HttpResponse response);
}
