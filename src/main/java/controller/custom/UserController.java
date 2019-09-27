package controller.custom;

import annotation.RequestMapping;
import controller.Controller;
import db.DataBase;
import model.User;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.ViewLocation;
import utils.HttpMethod;
import utils.HttpStatus;

import java.util.Map;

public class UserController implements Controller {
    private static final String INDEX_PAGE = "/index.html";
    private static final String LOGIN_FAILED_PAGE = "/user/login_failed.html";

    @RequestMapping(method = HttpMethod.POST, url = "/user/create")
    private void create(HttpRequest request, HttpResponse response) {
        Map<String, String> body = request.getBody();
        User user = new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
        DataBase.addUser(user);
        response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + INDEX_PAGE, HttpStatus.REDIRECT);
    }

    @RequestMapping(method = HttpMethod.POST, url = "/user/login")
    private void login(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getBodyValueBy("userId"));

        if (isSignedUpUser(request, user)) {
            response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + INDEX_PAGE, HttpStatus.REDIRECT);
            return;
        }
        response.sendRedirect(ViewLocation.TEMPLATE.getLocation() + LOGIN_FAILED_PAGE, HttpStatus.FORBIDDEN);
    }

    private boolean isSignedUpUser(HttpRequest request, User user) {
        return user != null && request.getBodyValueBy("password").equals(user.getPassword());
    }
}
