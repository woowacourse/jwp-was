package servlet;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatus;

public abstract class AbstractServlet implements Servlet {
    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        if (HttpMethod.GET.match(request.getMethod())) {
            doGet(request, response);
            return;
        }
        if (HttpMethod.POST.match(request.getMethod())) {
            doPost(request, response);
            return;
        }
        response.error(HttpStatus.METHOD_NOT_ALLOWED);
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
    }
}
