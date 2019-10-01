package controller;

import controller.core.AbstractController;
import webserver.http.HttpHeaderField;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.core.ResponseContentType;
import webserver.http.response.core.ResponseStatus;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceController extends AbstractController {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        doGet(httpRequest, httpResponse);
        httpResponse.sendResponse(httpRequest);
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.addStatus(ResponseStatus.of(STATUS_OK))
                .addHeader(HttpHeaderField.CONTENT_TYPE, ResponseContentType.of(httpRequest.getRequestPath()));
    }
}
