package controller;

import static http.request.RequestMethod.*;

import java.io.IOException;
import java.net.URISyntaxException;

import exception.HttpRequestMethodNotSupportedException;
import exception.IllegalRequestException;
import http.request.Request;
import http.response.Response;

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

    public void doGet(Request request, Response response) throws IOException, URISyntaxException {
        response.methodNotAllowed(request.getRequestMethod());
        throw new HttpRequestMethodNotSupportedException(request.getRequestMethod());
    }

    public void doPost(Request request, Response response) throws IllegalRequestException {
        response.methodNotAllowed(request.getRequestMethod());
        throw new HttpRequestMethodNotSupportedException(request.getRequestMethod());
    }
}
