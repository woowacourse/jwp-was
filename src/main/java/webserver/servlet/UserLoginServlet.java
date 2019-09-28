package webserver.servlet;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.view.ModelAndView;

import java.io.IOException;

public class UserLoginServlet extends RequestServlet {
    private final String url = "/user/login";

    public UserLoginServlet(Resolver resolver) {
        super(resolver);
    }

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return null;
    }

    @Override
    public ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String id = httpRequest.getBody("userId");
        String password = httpRequest.getBody("password");
        User user = DataBase.findUserById(id);
        if (canLogin(password, user)) {
            httpResponse.setCookie();
            return new ModelAndView(resolver.createView("redirect:/"));
        }
        return new ModelAndView(resolver.createView("redirect:/user/login-failed.html"));
    }

    private boolean canLogin(String password, User user) {
        return user != null && user.isCorrectPassword(password);
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
