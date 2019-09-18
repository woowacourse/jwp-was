package http.controller;

import http.model.HttpRequest;
import http.view.Model;
import http.view.ModelAndView;
import model.User;

public class ModelRequestHandler implements HttpRequestHandler {
    @Override
    public ModelAndView handle(HttpRequest httpRequest) {
        Model model = new Model();
        model.addAttributes("user", new User("andole", "abcd", "andole", "andole@andole.com"));
        return new ModelAndView(model);
    }
}
