package controller.page;

import controller.AbstractController;
import db.DataBase;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ObjectsForHandlebars;
import webserver.response.ResponseMetaData;
import webserver.response.ResponseMetaDataGenerator;

import java.io.IOException;

public class UserListPageController extends AbstractController {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        ResponseMetaData responseMetaData = ResponseMetaDataGenerator.buildDefaultFoundMetaData(request, "/user/login.html");
        if (isLogin(request)) {
            responseMetaData = ResponseMetaDataGenerator.buildDefaultOkMetaData(request);
        }

        response.setResponseMetaData(responseMetaData);
        doGet(request, response);
    }

    private boolean isLogin(final HttpRequest request) {
        return request.hasJSessionId();
    }

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        if (isLogin(request)) {
            ObjectsForHandlebars objectsForHandlebars = new ObjectsForHandlebars();
            objectsForHandlebars.put("users", DataBase.findAll());

            response.makeResponse(objectsForHandlebars);
            return;
        }

        response.makeResponse();
    }
}
