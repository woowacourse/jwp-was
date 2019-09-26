package webserver.servlet;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.View;

import java.io.IOException;

public class UserLoginServlet extends RequestServlet {
    private final String url = "/user/login";

    @Override
    public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String id = httpRequest.getBody("userId");
        String password = httpRequest.getBody("password");
        User user = DataBase.findUserById(id);
        if (canLogin(password, user)) {
            httpResponse.redirect("/");
            return new View(null);
        }
        httpResponse.redirect("/user/login_failed.html");
        return new View(null);
    }

    private boolean canLogin(String password, User user) {
        return user != null && user.isCorrectPassword(password);
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
