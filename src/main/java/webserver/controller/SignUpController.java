package webserver.controller;

import db.DataBase;
import model.User;
import webserver.HttpStatus;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class SignUpController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        doPost(httpRequest, httpResponse);
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"));
        DataBase.addUser(user);
        httpResponse.addStatusLine(httpRequest, HttpStatus.FOUND);
        httpResponse.addHeader(HEADER_FIELD_LOCATION, HTTP_PROTOCOL + httpRequest.getHeaderFieldValue(HEADER_FIELD_HOST) + "/index.html");
    }
}
