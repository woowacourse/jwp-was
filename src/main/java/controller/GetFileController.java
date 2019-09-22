package controller;

import http.request.Request2;
import http.request.RequestMethod;
import http.request.RequestUrlType;
import http.response.FileResponse2;
import http.response.Response2;

import java.util.Arrays;

public class GetFileController extends AbstractController2 {

    @Override
    public boolean isMapping(Request2 request) {
        return (request.getRequestMethod() == RequestMethod.GET)
                &&
                (isCorrectRequestUrlContentType(request));
    }

    @Override
    public Response2 createResponse(Request2 request) {
        if (isMapping(request)) {
            return createGetResponse(request);
        }
        return new PostUserController().createResponse(request);
    }

    @Override
    public Response2 createGetResponse(Request2 request) {
        return new FileResponse2(request.getUrl().getUrlPath(), request.getUrl().getRequestUrlType().getContentType());
    }

    private boolean isCorrectRequestUrlContentType(Request2 request) {
        return Arrays.stream(RequestUrlType.values())
                .anyMatch(type -> request.getUrl().getRequestUrlType() == type);
    }

}
