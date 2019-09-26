package http.controller;

import db.DataBase;
import http.model.request.ServletRequest;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;
import model.User;

public class LoginController extends AbstractController {
    public LoginController(RequestMapping mapping) {
        super(mapping);
    }

    public LoginController(RequestMapping... mappings) {
        super(mappings);
    }

    @Override
    public void handle(ServletRequest servletRequest, ServletResponse servletResponse) {
        String userId = servletRequest.getParameter("userId");
        String password = servletRequest.getParameter("password");

        User user = DataBase.findUserById(userId);

        if (authenticate(user, password)) {
            servletResponse.setCookie("logined=true; Path=/");
            servletResponse.redirect("/index.html");
            return;
        }

        servletResponse.setCookie("logined=false");
        servletResponse.redirect("/user/authenticate.html");
    }

    private boolean authenticate(User user, String password) {
        return user != null && user.matchPassword(password);
    }
}
