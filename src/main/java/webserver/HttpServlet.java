package webserver;

import static webserver.http.HttpMethod.*;

import webserver.exception.MethodNotAllowedException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

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
