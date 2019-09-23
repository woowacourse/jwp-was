package controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.OkResponseMetaData;
import webserver.response.ResponseMetaData;

import java.io.IOException;

public class ResourceController extends AbstractController {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        ResponseMetaData responseMetaData = new OkResponseMetaData(request);
        response.setResponseMetaData(responseMetaData);

        doGet(request, response);
    }
}
