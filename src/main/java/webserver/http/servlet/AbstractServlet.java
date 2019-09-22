package webserver.http.servlet;

import webserver.http.HttpStatus;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public abstract class AbstractServlet implements Servlet {
    @Override
    public void service(final HttpRequest request, final HttpResponse response) {
        final HttpMethod method = request.getMethod();

        if (method == HttpMethod.GET) {
            doGet(request, response);
        } else if (method == HttpMethod.POST) {
            doPost(request, response);
        } else if (method == HttpMethod.PUT) {
            doPut(request, response);
        } else if (method == HttpMethod.DELETE) {
            doDelete(request, response);
        } else {
            response.sendError(HttpStatus.NOT_FOUND, "not support this method");
        }
    }

    protected void doPost(final HttpRequest request, final HttpResponse response) {
        response.sendError(HttpStatus.NOT_FOUND, "not support Post");
    }

    protected void doGet(final HttpRequest request, final HttpResponse response) {
        response.sendError(HttpStatus.NOT_FOUND, "not support Get");
    }

    protected void doPut(final HttpRequest request, final HttpResponse response) {
        response.sendError(HttpStatus.NOT_FOUND, "not support Put");
    }

    protected void doDelete(final HttpRequest request, final HttpResponse response) {
        response.sendError(HttpStatus.NOT_FOUND, "not support Delete");
    }
}
