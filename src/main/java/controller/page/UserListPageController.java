package controller.page;

import controller.AbstractController;
import db.DataBase;
import view.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
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

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        if (isLogin(request)) {
            ModelAndView modelAndView = new ModelAndView(request.findUri());
            modelAndView.addObject("users", DataBase.findAll());

            response.makeResponse(modelAndView);
            return;
        }

        response.makeResponse();
    }
}
