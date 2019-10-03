package controller;

import db.DataBase;
import http.HttpSessionManager;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import view.Model;
import view.ModelAndView;
import view.RedirectView;
import view.View;

import java.util.Arrays;
import java.util.Collection;

public class UserListController extends AbstractController {

    @Override
    public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String cookie = httpRequest.getHeaderAttribute("Cookie");
        if (cookie != null && isValidJSession(cookie)) {
            Collection<User> users = DataBase.findAll();
            Model model = new Model();
            model.setAttribute("users", users);

            return new ModelAndView(model, "user/list");
        }

        return new RedirectView("user/login.html");
    }

    private boolean isValidJSession(String cookie) {
        return Arrays.stream(cookie.split(";"))
                .filter(value -> value.contains("JSESSIONID"))
                .findAny()
                .map(session -> {
                    String[] tokens = session.split("=");
                    return HttpSessionManager.existSession(tokens[1].trim());
                })
                .orElse(false)
                ;
    }
}
