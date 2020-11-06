package webserver;

import exception.NotSupportedMethodException;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;

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

    protected void doGet(HttpRequest request, HttpResponse response) {
        response.response(HttpStatus.BAD_REQUEST);
        throw new NotSupportedMethodException(GET);
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
        response.response(HttpStatus.BAD_REQUEST);
        throw new NotSupportedMethodException(POST);
    }

    protected void doPut(HttpRequest request, HttpResponse response) {
        response.response(HttpStatus.BAD_REQUEST);
        throw new NotSupportedMethodException(PUT);
    }

    protected void doDelete(HttpRequest request, HttpResponse response) {
        response.response(HttpStatus.BAD_REQUEST);
        throw new NotSupportedMethodException(DELETE);
    }
}
