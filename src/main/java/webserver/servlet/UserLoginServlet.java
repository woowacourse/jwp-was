package webserver.servlet;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.ModelAndView;

import java.io.IOException;

public class UserLoginServlet extends RequestServlet {
    private final String url = "/user/login";

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.ok();
        return new ModelAndView(url);
    }

    @Override
    public ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String id = httpRequest.getBody("userId");
        String password = httpRequest.getBody("password");
        User user = DataBase.findUserById(id);
        if (canLogin(password, user)) {
            httpResponse.redirect("/");
            httpResponse.setCookie();
            return new ModelAndView(null);
        }
        httpResponse.redirect("/user/login_failed.html");
        return new ModelAndView(null);
    }

    private boolean canLogin(String password, User user) {
        return user != null && user.isCorrectPassword(password);
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
