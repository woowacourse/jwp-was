package domain.controller;

import domain.db.UserDataBase;
import domain.model.User;
import mvc.annotation.RequestMapping;
import mvc.controller.AbstractController;
import mvc.view.ForwardView;
import mvc.view.RedirectView;
import mvc.view.View;
import server.http.request.HttpRequest;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RequestMapping(urlPath = "/signup")
public class SignupController extends AbstractController {
    @Override
    public View get(HttpRequest httpRequest) {
        return new ForwardView("user/form.html");
    }

    @Override
    public View post(HttpRequest httpRequest) {
        try {
            UserDataBase.addUser(new User(
                    URLDecoder.decode(httpRequest.getBody("userId"), StandardCharsets.UTF_8.toString()),
                    URLDecoder.decode(httpRequest.getBody("password"), StandardCharsets.UTF_8.toString()),
                    URLDecoder.decode(httpRequest.getBody("name"), StandardCharsets.UTF_8.toString()),
                    URLDecoder.decode(httpRequest.getBody("email"), StandardCharsets.UTF_8.toString())
            ));
            return new RedirectView("/user/login");
        } catch (Exception e) {
            return new RedirectView("/signup");
        }
    }
}
