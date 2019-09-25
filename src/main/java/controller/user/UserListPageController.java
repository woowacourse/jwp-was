package controller.user;

import controller.AbstractController;
import db.DataBase;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.ObjectsForHandlebars;
import webserver.response.ResponseMetaData;

import java.io.IOException;

import static webserver.request.RequestHeaderFieldKeys.JSESSIONID;

public class UserListPageController extends AbstractController {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        ResponseMetaData responseMetaData = buildSuccessfulResponseMetaData(request);
        if (isNotLogin(request)) {
            responseMetaData = buildFailedResponseMetaData(request);
        }

        response.setResponseMetaData(responseMetaData);
        doGet(request, response);
    }

    private boolean isNotLogin(final HttpRequest request) {
        return request.findHeaderField(JSESSIONID) == null;
    }

    private ResponseMetaData buildSuccessfulResponseMetaData(final HttpRequest request) {
        return ResponseMetaData.Builder
                .builder(request, HttpStatus.OK)
                .contentType(request.findContentType())
                .build();
    }

    private ResponseMetaData buildFailedResponseMetaData(final HttpRequest request) {
        return ResponseMetaData.Builder
                .builder(request, HttpStatus.FOUND)
                .location("/user/login.html")
                .build();
    }

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        if (isNotLogin(request)) {
            response.makeResponse();
            return;
        }

        ObjectsForHandlebars objectsForHandlebars = new ObjectsForHandlebars();
        objectsForHandlebars.put("users", DataBase.findAll());

        response.makeResponse(objectsForHandlebars);
    }
}
