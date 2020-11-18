package controller;

import db.UserRepository;
import http.request.QueryParams;
import http.request.Request;
import http.response.Response;
import model.User;

public class UserCreateController extends AbstractController {

    @Override
    protected void doGet(Request request, Response response) {
        QueryParams queryParams = request.getQueryParams();

        if (!queryParams.isEmpty()) {
            User user = new User(queryParams.getParam("userId"), queryParams.getParam("password"),
                    queryParams.getParam("name"), queryParams.getParam("email"));
            UserRepository.addUser(user);
            response.found("/index.html");
        }
    }

    @Override
    protected void doPost(Request request, Response response) {
        String userId = request.getParam("userId");
        String password = request.getParam("password");
        String name = request.getParam("name");
        String email = request.getParam("email");
        User user = new User(userId, password, name, email);
        UserRepository.addUser(user);

        response.found("/index.html");
    }
}
