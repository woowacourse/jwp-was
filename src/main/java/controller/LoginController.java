package controller;

import java.util.Objects;

import db.DataBase;
import http.request.HttpRequest;
import http.request.HttpRequestMapping;
import model.User;
import view.Model;
import view.ModelAndView;
import view.View;

public class LoginController extends HttpRequestMappingAbstractController {

    public LoginController(HttpRequestMapping httpRequestMapping) {
        super(httpRequestMapping);
    }

    @Override
    public ModelAndView handle(HttpRequest httpRequest) {
        String userId = httpRequest.getRequestBody().parseBody().get("userId");
        String password = httpRequest.getRequestBody().parseBody().get("password");

        User user = DataBase.findUserById(userId);

        if (authenticateUser(user, password)) {
            Model model = new Model();
            model.addAttributes("user", user);
            View view = new View("redirect:/index.html");
            return ModelAndView.of(model, view);
        }
        Model model = new Model();
        model.addAttributes("user", user);
        View view = new View("redirect:/user/login_failed.html");

        return ModelAndView.of(model, view);

    }

    private boolean authenticateUser(User user, String password) {
        return Objects.nonNull(user) && user.validatePassword(password);
    }
}
