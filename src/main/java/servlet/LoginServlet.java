package servlet;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponseEntity;
import model.User;

public class LoginServlet extends AbstractServlet {
    @Override
    protected HttpResponseEntity doPost(HttpRequest httpRequest) {
        String userId = httpRequest.getParam("userId");
        String password = httpRequest.getParam("password");
        if (login(userId, password)) {
            return HttpResponseEntity.get302Response("/index.html");
        }
        return HttpResponseEntity.get302Response("/user/login_failed.html");
    }

    private boolean login(String userId, String password) {
        User user = DataBase.findUserById(userId);
        if (user == null) {
            return false;
        }
        return user.matchPassword(password);
    }
}
