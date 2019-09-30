package controller.custom;

import controller.Controller;
import controller.annotation.RequestMapping;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.http.*;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

public class UserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String INDEX_PAGE = "index";

    @RequestMapping(method = HttpMethod.POST, url = "/user/create")
    private ModelAndView create(HttpRequest request, HttpResponse response) {
        Map<String, String> body = request.getBody();
        User user = new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
        DataBase.addUser(user);
        return new ModelAndView(INDEX_PAGE, HttpStatus.REDIRECT);
    }

    @RequestMapping(method = HttpMethod.POST, url = "/user/login")
    private ModelAndView login(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getBodyValueBy("userId"));

        if (matchedUser(request, user)) {
            HttpSession session = request.getHttpSession();
            session.setAttribute("user", user);
            response.addCookie(new Cookie("JSESSIONID", session.getJSESSIONID()));
            return new ModelAndView(INDEX_PAGE, HttpStatus.REDIRECT);
        }
        return new ModelAndView("user/login_failed", HttpStatus.REDIRECT);
    }

    private boolean matchedUser(HttpRequest request, User user) {
        return user != null && user.getPassword().equals(request.getBodyValueBy("password"));
    }

    @RequestMapping(method = HttpMethod.GET, url = "/user/list")
    private ModelAndView getUserList(HttpRequest request, HttpResponse response) {
        ModelAndView modelAndView = new ModelAndView();

        if (isLoggedIn(request)) {
            modelAndView.setModel("userList", DataBase.findAll());
            modelAndView.setView("user/list");
            modelAndView.setHttpStatus(HttpStatus.OK);
            return modelAndView;
        }
        modelAndView.setView("user/login");
        modelAndView.setHttpStatus(HttpStatus.REDIRECT);
        return modelAndView;
    }

    private boolean isLoggedIn(HttpRequest request) {
        return request.getHttpSession().getAttribute("user") != null;
    }
}
