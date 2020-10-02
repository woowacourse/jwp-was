package controller;

import static http.request.RequestMethod.*;

import java.util.Map;

import db.DataBase;
import http.request.Request;
import http.response.Response;
import mapper.QueryParams;
import model.User;

public class UserCreateController implements Controller {
    @Override
    public void service(Request request, Response response) {
        if (request.isMethod(GET)) {
            doGet(request, response);
        } else if (request.isMethod(POST)) {
            doPost(request, response);
        }
    }

    private void doGet(Request request, Response response) {
        String requestUrl = request.getRequestLine().getUrl();
        QueryParams queryParams = new QueryParams(requestUrl);
        Map<String, String> queryParamsMap = queryParams.getQueryParams();

        if (!queryParamsMap.isEmpty()) {
            User user = new User(queryParamsMap.get("userId"), queryParamsMap.get("password"),
                    queryParamsMap.get("name"), queryParamsMap.get("email"));
            DataBase.addUser(user);
            response.found("/index.html");
        }
    }

    private void doPost(Request request, Response response) {
        Map<String, String> requestBodies = request.getRequestBody().parseRequestBody();
        User user = new User(requestBodies.get("userId"), requestBodies.get("password"),
                requestBodies.get("name"), requestBodies.get("email"));
        DataBase.addUser(user);

        response.found("/index.html");
    }
}
