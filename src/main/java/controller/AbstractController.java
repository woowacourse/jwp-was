package controller;

import exception.HttpRequestMethodNotSupportedException;
import exception.IllegalRequestException;
import http.request.Request;
import http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.request.RequestMethod.GET;
import static http.request.RequestMethod.POST;

public abstract class AbstractController implements Controller {

    @Override
    public void service(Request request, Response response) throws
        IOException,
        URISyntaxException,
        IllegalRequestException {
        if (request.isMethod(GET)) {
            doGet(request, response);
        } else if (request.isMethod(POST)) {
            doPost(request, response);
        }
    }

    protected void doGet(Request request, Response response) throws IOException, URISyntaxException {
        response.methodNotAllowed(request.getRequestMethod());
        throw new HttpRequestMethodNotSupportedException(request.getRequestMethod());
    }

    protected void doPost(Request request, Response response) throws IllegalRequestException {
        response.methodNotAllowed(request.getRequestMethod());
        throw new HttpRequestMethodNotSupportedException(request.getRequestMethod());
    }

    protected void doDelete(Request request, Response response) {
        response.methodNotAllowed(request.getRequestMethod());
        throw new HttpRequestMethodNotSupportedException(request.getRequestMethod());
    }
}
