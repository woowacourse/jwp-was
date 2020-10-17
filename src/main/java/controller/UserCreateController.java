package controller;

import db.DataBase;
import exception.IllegalRequestException;
import http.request.QueryParams;
import http.request.Request;
import http.request.RequestBody;
import http.response.Response;
import model.User;

import java.util.Map;

public class UserCreateController extends AbstractController {

    @Override
    protected void doGet(Request request, Response response) {
        QueryParams queryParams = request.getQueryParams();

        if (!queryParams.isEmpty()) {
            User user = new User(queryParams.getParam("userId"), queryParams.getParam("password"),
                    queryParams.getParam("name"), queryParams.getParam("email"));
            DataBase.addUser(user);
            response.found("/index.html");
        }
    }

    @Override
    protected void doPost(Request request, Response response) throws IllegalRequestException {
        RequestBody requestBody = request.getRequestBody();
        Map<String, String> requestBodies = requestBody.parseRequestBody();

        User user = new User(requestBodies.get("userId"), requestBodies.get("password"),
                requestBodies.get("name"), requestBodies.get("email"));
        DataBase.addUser(user);

        response.found("/index.html");
    }
}
