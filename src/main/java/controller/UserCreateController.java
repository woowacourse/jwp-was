package controller;

import db.DataBase;
import exception.IllegalRequestException;
import http.request.QueryParams;
import http.request.Request;
import http.response.Response;
import model.User;

import java.util.Map;

public class UserCreateController extends AbstractController {

    @Override
    public void doGet(Request request, Response response) {
        String requestUrl = request.getPath();
        QueryParams queryParams = new QueryParams(requestUrl);
        Map<String, String> queryParamsMap = queryParams.getQueryParams();

        if (!queryParamsMap.isEmpty()) {
            User user = new User(queryParamsMap.get("userId"), queryParamsMap.get("password"),
                    queryParamsMap.get("name"), queryParamsMap.get("email"));
            DataBase.addUser(user);
            response.found("/index.html");
        }
    }

    @Override
    public void doPost(Request request, Response response) throws IllegalRequestException {
        Map<String, String> requestBodies = request.getRequestBody().parseRequestBody();
        User user = new User(requestBodies.get("userId"), requestBodies.get("password"),
                requestBodies.get("name"), requestBodies.get("email"));
        DataBase.addUser(user);

        response.found("/index.html");
    }
}
