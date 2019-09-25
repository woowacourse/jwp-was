package controller.page;

import controller.AbstractController;
import db.DataBase;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ObjectsForHandlebars;
import webserver.response.ResponseMetaData;
import webserver.response.ResponseMetaDataGenerator;

import java.io.IOException;

import static webserver.request.RequestHeaderFieldKeys.JSESSIONID;

public class UserListPageController extends AbstractController {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        ResponseMetaData responseMetaData = ResponseMetaDataGenerator.buildDefaultOkMetaData(request);
        if (isNotLogin(request)) {
            responseMetaData = ResponseMetaDataGenerator.buildDefaultFoundMetaData(request, "/user/login.html");
        }

        response.setResponseMetaData(responseMetaData);
        doGet(request, response);
    }

    private boolean isNotLogin(final HttpRequest request) {
        return request.findHeaderField(JSESSIONID) == null;
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
