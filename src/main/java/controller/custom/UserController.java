package controller.custom;

import annotation.RequestMapping;
import controller.Controller;
import db.DataBase;
import model.User;
import model.http.Cookie;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.HttpSession;
import utils.HttpMethod;
import utils.HttpStatus;

import java.util.Map;

public class UserController implements Controller {
    private static final String INDEX_PAGE = "/index.html";

    @RequestMapping(method = HttpMethod.POST, url = "/user/create")
    private void create(HttpRequest request, HttpResponse response) {
        Map<String, String> body = request.getBody();
        User user = new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
        DataBase.addUser(user);
        response.sendRedirect(INDEX_PAGE, HttpStatus.REDIRECT);
    }

    @RequestMapping(method = HttpMethod.POST, url = "/user/login")
    private void login(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getBodyValueBy("userId"));
        HttpSession session = request.getHttpSession();
        if (isSignedUp(request, user)) {
            session.setAttribute("user", user);
            response.addCookie(new Cookie("id", session.getId()));
            response.sendRedirect(INDEX_PAGE, HttpStatus.REDIRECT);
            return;
        }
        response.sendRedirect("/user/login_failed.html", HttpStatus.REDIRECT);
    }

    private boolean isSignedUp(HttpRequest request, User user) {
        return user != null && user.getPassword().equals(request.getBodyValueBy("password"));
    }


    @RequestMapping(method = HttpMethod.GET, url = "/user/list")
    private void getUserList(HttpRequest request, HttpResponse response) {
        HttpSession session = request.getHttpSession();
        if (session.getAttribute("user") != null) {
            response.setBody("userList", DataBase.findAll());
            response.sendRedirect("/user/list.html", HttpStatus.OK);
            return;
        }
        response.sendRedirect("/user/login.html", HttpStatus.REDIRECT);
    }

}
