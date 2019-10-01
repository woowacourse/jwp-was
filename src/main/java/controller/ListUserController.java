package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import model.User;

import java.util.Collection;

public class ListUserController extends AbstractController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        if (!isLogined(request.getSession())) {
            response.sendRedirect("/user/login.html");
            return;
        }

        Collection<User> users = DataBase.findAll();
        request.addAttribute("users", users);

        response.forward("/user/list");
    }

    private static boolean isLogined(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return false;
        }
        return true;
    }
}
