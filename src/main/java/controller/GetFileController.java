package controller;

import http.request.Request;
import http.request.RequestMethod;
import http.request.RequestContentType;
import http.response.FileResponse;
import http.response.Response;

import java.util.Arrays;

public class GetFileController extends AbstractController {

    @Override
    public boolean isMapping(Request request) {
        return (request.getRequestMethod() == RequestMethod.GET)
                &&
                (isCorrectRequestContentType(request));
    }

    @Override
    public Response createResponse(Request request) {
        if (isMapping(request)) {
            return createGetResponse(request);
        }
        return new PostUserController().createResponse(request);
    }

    @Override
    public Response createGetResponse(Request request) {
        return new FileResponse(request.getUrl().getUrlPath(), request.getUrl().getRequestContentType().getContentType());
    }

    private boolean isCorrectRequestContentType(Request request) {
        return Arrays.stream(RequestContentType.values())
                .anyMatch(type -> request.getUrl().getRequestContentType() == type);
    }

}
