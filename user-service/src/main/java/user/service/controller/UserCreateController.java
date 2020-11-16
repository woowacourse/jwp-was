package user.service.controller;

import java.util.Map;

import http.was.controller.annotation.Controller;
import http.was.controller.annotation.RequestMapping;
import http.was.exception.IllegalRequestException;
import http.was.http.HttpMethod;
import http.was.http.request.QueryParams;
import http.was.http.request.Request;
import http.was.http.request.RequestBody;
import http.was.http.response.Response;
import user.service.db.DataBase;
import user.service.model.User;

@Controller
public class UserCreateController {

    @RequestMapping(path = "/users", method = HttpMethod.GET)
    public void doGet(Request request, Response response) {
        QueryParams queryParams = request.getQueryParams();

        if (!queryParams.isEmpty()) {
            User user = new User(queryParams.getParam("userId"), queryParams.getParam("password"),
                    queryParams.getParam("name"), queryParams.getParam("email"));
            DataBase.addUser(user);
            response.found("/index.html");
        }
    }

    @RequestMapping(path = "/users", method = HttpMethod.POST)
    public void doPost(Request request, Response response) throws IllegalRequestException {
        RequestBody requestBody = request.getRequestBody();
        Map<String, String> requestBodies = requestBody.parseRequestBody();

        User user = new User(requestBodies.get("userId"), requestBodies.get("password"),
                requestBodies.get("name"), requestBodies.get("email"));
        DataBase.addUser(user);

        response.found("/index.html");
    }
}
