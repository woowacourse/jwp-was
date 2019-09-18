package http.controller;

import http.model.HttpParameters;
import http.model.HttpRequest;
import http.view.Model;
import http.view.ModelAndView;
import http.view.View;
import model.User;

public class UserRequestHandler implements HttpRequestHandler {
    @Override
    public ModelAndView handle(HttpRequest httpRequest) {
        HttpParameters httpParameters = httpRequest.getParameters();

        User user = new User(httpParameters.getParameter("userId"), httpParameters.getParameter("password"),
                httpParameters.getParameter("name"), httpParameters.getParameter("email"));

        Model model = new Model();
        model.addAttributes("user", user);
        View view = new View("redirect:/index.html");
        return new ModelAndView(model, view);
    }
}
