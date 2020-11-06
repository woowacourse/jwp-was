package webserver;

import http.HttpRequest;
import http.HttpResponse;

public abstract class HttpServlet implements Servlet {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        switch (request.getMethod()) {
            case GET:
                doGet(request, response);
            case POST:
                doPost(request, response);
            case PUT:
                doPut(request, response);
            case DELETE:
                doDelete(request, response);
        }
    }

    public void doGet(HttpRequest request, HttpResponse response) {
    }

    public void doPost(HttpRequest request, HttpResponse response) {
    }

    public void doPut(HttpRequest request, HttpResponse response) {
    }

    public void doDelete(HttpRequest request, HttpResponse response) {
    }
}
