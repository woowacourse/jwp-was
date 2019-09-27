package servlet.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import model.UserService;
import servlet.view.View;

import java.io.IOException;

import static servlet.view.ViewResolver.resolve;

public class UserListController extends HttpController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        UserService userService = new UserService();

        if ("true".equals(httpRequest.getCookie("logined"))) {
            View view = resolve("/user/list.html");
            view.addModel("users", userService.findAll());
            httpResponse.forward(view);
        }
        httpResponse.forward(resolve("/index.html"));
    }
}
