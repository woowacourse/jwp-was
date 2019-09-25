package controller.page;

import controller.AbstractController;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseMetaData;
import webserver.response.ResponseMetaDataGenerator;

import java.io.IOException;

import static webserver.request.RequestHeaderFieldKeys.JSESSIONID;

public class UserLoginPageController extends AbstractController {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        ResponseMetaData responseMetaData = ResponseMetaDataGenerator.buildDefaultFoundMetaData(request, "/index.html");
        if (isNotLogin(request)) {
            responseMetaData = ResponseMetaDataGenerator.buildDefaultOkMetaData(request);
        }
        response.setResponseMetaData(responseMetaData);

        doGet(request, response);
    }

    private boolean isNotLogin(final HttpRequest request) {
        return request.findHeaderField(JSESSIONID) == null;
    }
}
