package domain.controller;

import domain.service.LoginService;
import mvc.annotation.RequestMapping;
import mvc.controller.AbstractController;
import mvc.view.ForwardView;
import mvc.view.RedirectView;
import mvc.view.View;
import server.http.request.HttpRequest;
import was.http.context.Session;

@RequestMapping(urlPath = "/user/login")
public class LoginController extends AbstractController {
    @Override
    public View get(HttpRequest httpRequest) {
        return new ForwardView("user/login.html");
    }

    @Override
    public View post(HttpRequest httpRequest) {
        String id = httpRequest.getBody("userId");
        String password = httpRequest.getBody("password");

        LoginService loginService = LoginService.getInstance();
        if (loginService.validate(id, password)) {
            Session session = httpRequest.getSession();
            session.add("login", "true");
            session.add("userId", id);
            return new RedirectView("/");
        }
        return new RedirectView("/user/login");
    }
}
