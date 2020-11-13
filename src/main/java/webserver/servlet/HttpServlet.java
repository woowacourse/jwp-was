package webserver.servlet;

import static webserver.servlet.http.HttpMethod.*;

import webserver.servlet.exception.MethodNotAllowedException;
import webserver.servlet.http.request.HttpRequest;
import webserver.servlet.http.response.HttpResponse;

public abstract class HttpServlet extends GenericServlet {

    @Override
    public void doService(HttpRequest request, HttpResponse response) {
        if (request.isGetMethod()) {
            this.doGet(request, response);
            return;
        }
        this.doPost(request, response);
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowedException(GET);
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowedException(POST);
    }
}
