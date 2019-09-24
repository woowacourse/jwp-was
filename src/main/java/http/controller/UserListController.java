package http.controller;

import http.common.Cookie;
import http.exception.InvalidHeaderException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class UserListController extends AbstractController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            Cookie cookie = request.getCookie("logined");
            if (cookie.getValue().equals("false")) {
                setNotLoginedResponse(response);
            }
        } catch (InvalidHeaderException e) {
            setNotLoginedResponse(response);
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        //Todo
    }

    private void setNotLoginedResponse(HttpResponse response) {
        response.sendRedirect("/user/login.html");
        response.addCookie("logined", "false");
    }
}
