package controller;

import db.DataBase;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.ObjectsForHandlebars;
import webserver.response.ResponseMetaData;

import java.io.IOException;

import static webserver.request.RequestHeaderFieldKeys.LOGINED;

public class UserListController extends AbstractController {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        ResponseMetaData responseMetaData = ResponseMetaData.Builder
                .builder(request, HttpStatus.FOUND)
                .location("/user/login.html")
                .build();
        if ("true".equals(request.findHeaderField(LOGINED))) {
            responseMetaData = buildSuccessResponseMetaData(request);
        }


        response.setResponseMetaData(responseMetaData);
        doGet(request, response);
    }

    private ResponseMetaData buildSuccessResponseMetaData(final HttpRequest request) {
        return ResponseMetaData.Builder
                .builder(request, HttpStatus.OK)
                .contentType(request.findContentType())
                .build();
    }

    @Override
    void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        if (isNotLogin(request)) {
            response.makeResponse();
            return;
        }

        ObjectsForHandlebars objectsForHandlebars = new ObjectsForHandlebars();
        objectsForHandlebars.put("users", DataBase.findAll());

        response.makeResponse(objectsForHandlebars);
    }

    private boolean isNotLogin(final HttpRequest request) {
        return !"true".equals(request.findHeaderField(LOGINED));
    }
}
