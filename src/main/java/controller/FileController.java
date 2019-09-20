package controller;

import http.request.Request;
import http.response.Response;
import http.response.ResponseFactory;

public class FileController implements Controller{

    private Request request;

    public FileController(Request request) {
        this.request = request;
    }

    public Response home(Request request) {
        return ResponseFactory.getResponse(request);
    }

    @Override
    public Response createResponse() {
        return ResponseFactory.getResponse(request);
    }
}
