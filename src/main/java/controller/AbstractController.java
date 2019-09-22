package controller;

import http.request.Request;
import http.response.Response;

public class AbstractController implements Controller {

    @Override
    public boolean isMapping(Request request) {
        return false;
    }

    @Override
    public Response createResponse(Request request) {
        if (isMapping(request)) {
            createGetResponse(request);
            createPostResponse(request);
        }
        return new GetFileController().createResponse(request);
    }

    public Response createGetResponse(Request request) {
        return null; //create error response
    }

    public Response createPostResponse(Request request) {
        return null;
    }
}
