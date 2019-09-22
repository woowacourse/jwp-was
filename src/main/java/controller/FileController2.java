package controller;

import http.request.Request2;
import http.response.Response2;

public class FileController2 implements Controller2 {
    private Request2 request;

    public FileController2(Request2 request) {
        this.request = request;
    }

    @Override
    public boolean isMapping(Request2 request2) {
        return false;
    }

    @Override
    public Response2 createResponse(Request2 request) {
        return null;
    }
}
