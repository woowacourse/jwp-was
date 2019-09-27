package domain.controller;

import mvc.annotation.RequestMapping;
import mvc.controller.AbstractController;
import mvc.view.ForwardView;
import mvc.view.RedirectView;
import mvc.view.View;
import server.http.request.HttpRequest;

@RequestMapping(urlPath = "/user/login")
public class LoginController extends AbstractController {
    @Override
    public View get(HttpRequest httpRequest) {
        return new ForwardView("user/login.html");
    }

    @Override
    public View post(HttpRequest httpRequest) {
        return new RedirectView("/");
    }
}
