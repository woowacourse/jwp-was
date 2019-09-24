package controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.ResponseMetaData;

import java.io.IOException;

public class ResourceController extends AbstractController {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        ResponseMetaData responseMetaData = ResponseMetaData.Builder
                .aResponseMeatData2(request, HttpStatus.OK)
                .contentType(request.findContentType())
                .build();
        response.setResponseMetaData(responseMetaData);

        doGet(request, response);
    }
}
