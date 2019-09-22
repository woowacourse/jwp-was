package controller;

import http.request.Request;
import http.request.RequestMethod;
import http.request.RequestUrlType;
import http.response.FileResponse;
import http.response.Response;

import java.util.Arrays;

public class GetFileController extends AbstractController {

    @Override
    public boolean isMapping(Request request) {
        return (request.getRequestMethod() == RequestMethod.GET)
                &&
                (isCorrectRequestUrlContentType(request));
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
        return new FileResponse(request.getUrl().getUrlPath(), request.getUrl().getRequestUrlType().getContentType());
    }

    private boolean isCorrectRequestUrlContentType(Request request) {
        return Arrays.stream(RequestUrlType.values())
                .anyMatch(type -> request.getUrl().getRequestUrlType() == type);
    }

}
