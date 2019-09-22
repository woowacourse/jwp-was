package controller;

import http.request.Request2;
import http.response.Response2;

public class AbstractController2 implements Controller2{

    @Override
    public boolean isMapping(Request2 request) {
        return false;
    }

    @Override
    public Response2 createResponse(Request2 request) {
        if (isMapping(request)) {
            createGetResponse(request);
            createPostResponse(request);
        }
        return new GetFileController().createResponse(request);
    }

    public Response2 createGetResponse(Request2 request) {
        return null; //create error response
    }

    public Response2 createPostResponse(Request2 request) {
        return null;
    }
}
