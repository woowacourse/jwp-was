package webserver.controller;

import db.DataBase;
import factory.UserFactory;
import model.User;
import webserver.controller.request.HttpRequest;
import webserver.controller.request.body.HttpRequestBody;
import webserver.controller.response.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;

public class UserController {
    public static final String SAVE_REDIRECT_UTL = "/index.html";
    public static void save(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String requestParam = httpRequest.readData();
            HttpRequestBody httpRequestBody = new HttpRequestBody(requestParam);
            User user = UserFactory.of(httpRequestBody.getBodyDataSet());
            DataBase.addUser(user);
            httpResponse.redirect302Found(SAVE_REDIRECT_UTL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
