package servlet.controller;

import domain.UserService;
import http.request.HttpRequest;
import http.response.HttpResponse;
import servlet.view.View;

import java.io.IOException;
import java.util.Optional;

import static servlet.view.support.ViewResolver.resolve;

public class UserListController extends HttpController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        UserService userService = new UserService();

        boolean isLogin = Optional.ofNullable(httpRequest.getSessionAttribute("logined")).isPresent();

        if (isLogin) {
            View view = resolve("/user/list.html");
            view.addModel("users", userService.findAll());
            httpResponse.forward(view);
            return;
        }
        httpResponse.addHeader("Location", "/index.html");
        httpResponse.sendRedirect();
    }
}
