package controller.user;

import controller.AbstractController;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.ResponseMetaData;

import java.io.IOException;

import static webserver.request.RequestHeaderFieldKeys.JSESSIONID;

public class UserLoginPageController extends AbstractController {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        ResponseMetaData responseMetaData = buildFailedResponseMetaData(request);
        if (isNotLogin(request)) {
            responseMetaData = buildSuccessfulResponseMetaData(request);
        }
        response.setResponseMetaData(responseMetaData);

        doGet(request, response);
    }

    private ResponseMetaData buildFailedResponseMetaData(final HttpRequest request) {
        return ResponseMetaData.Builder
                .builder(request, HttpStatus.FOUND)
                .location("/index.html")
                .build();
    }

    private ResponseMetaData buildSuccessfulResponseMetaData(final HttpRequest request) {
        return ResponseMetaData.Builder
                .builder(request, HttpStatus.OK)
                .contentType(request.findContentType())
                .build();
    }

    private boolean isNotLogin(final HttpRequest request) {
        return request.findHeaderField(JSESSIONID) == null;
    }
}
