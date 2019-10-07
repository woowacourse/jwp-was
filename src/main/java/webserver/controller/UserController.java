package webserver.controller;

import db.DataBase;
import factory.UserFactory;
import model.User;
import webserver.ModelAndView;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class UserController extends AbstractController {
    private static final String SAVE_REDIRECT_URL = "/index.html";

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.applyTemplateEngine(httpRequest.getPath());

        return HttpResponse.ok(httpRequest, modelAndView.getView());
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        Map<String, String> requestBodyFields = httpRequest.getBodyFields();
        User user = UserFactory.of(requestBodyFields);
        DataBase.addUser(user);
        return HttpResponse.sendRedirect(httpRequest, SAVE_REDIRECT_URL);
    }
}
