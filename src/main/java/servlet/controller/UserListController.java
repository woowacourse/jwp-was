package servlet.controller;

import http.response.HttpResponse;
import http.request.HttpRequest;
import model.User;
import model.UserService;
import webserver.support.PathHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends HttpController {

    @Override
    //@TODO refactoring
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        UserService userService = new UserService();

        if ("true".equals(httpRequest.getCookie("logined"))) {
            Collection<User> users = userService.findAll();
            Map<String, Object> model = new HashMap<>();
            model.put("users", users);
            httpResponse.addModel(model);
            httpResponse.forward(PathHandler.path("/user/list.html"));
        }
        httpResponse.forward(PathHandler.path("/index.html"));
    }
}
