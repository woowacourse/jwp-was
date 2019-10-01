package webserver.http.servlet;

import webserver.http.response.HttpStatus;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractServlet implements Servlet {
    public Map<HttpMethod, Servlet> map = new HashMap<>();

    {
        map.put(HttpMethod.GET, this::doGet);
        map.put(HttpMethod.POST, this::doPost);
        map.put(HttpMethod.PUT, this::doPut);
        map.put(HttpMethod.DELETE, this::doDelete);
    }

    @Override
    public void service(final HttpRequest request, final HttpResponse response) {
        final HttpMethod method = request.getMethod();
        map.get(method).service(request, response);
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
