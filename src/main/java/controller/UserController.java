package controller;

import db.DataBase;
import java.util.Map;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import response.StatusCode;

public class UserController {

    public HttpResponse createUser(HttpRequest request) {
        Map<String, String> queryData = request.getFormDataFromBody();

        User user = new User(
            queryData.get("userId"),
            queryData.get("password"),
            queryData.get("name"),
            queryData.get("email")
        );
        DataBase.addUser(user);

        return new HttpResponse(StatusCode.FOUND, "/");
    }
}
