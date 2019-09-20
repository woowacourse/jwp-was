package http.controller;

import http.model.HttpParameters;
import http.model.HttpRequest;
import http.supoort.RequestMapping;
import http.view.Model;
import http.view.ModelAndView;
import http.view.View;
import model.User;

public class UserController extends AbstractController {

    public UserController(RequestMapping requestMapping) {
        super(requestMapping);
    }

    public UserController(RequestMapping... mappings) {
        super(mappings);
    }

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
