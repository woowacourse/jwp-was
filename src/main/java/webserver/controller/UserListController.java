package webserver.controller;

import db.DataBase;
import model.User;
import webserver.ModelAndView;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.io.IOException;
import java.util.Collection;

public class UserListController extends AbstractController {
    private static final String USER_LIST_URL = "/user/list.html";
    private static final String NON_LOGIN_REDIRECT_URL = "/user/login.html";

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        String[] logined = httpRequest.getHeaderFieldValue("Cookie").split("=");

        if (logined[1].equals("true")) {
            Collection<User> users = DataBase.findAll();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addModel("users", users);
            modelAndView.applyTemplateEngine(USER_LIST_URL);

            return HttpResponse.ok(httpRequest, modelAndView.getView());
        }

        return HttpResponse.sendRedirect(httpRequest, NON_LOGIN_REDIRECT_URL, false);
    }
}
