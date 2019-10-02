package servlet;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.HttpSession;
import model.User;

import static model.User.USER_ID_KEY;
import static model.User.USER_PASSWORD_KEY;

public class LoginServlet extends AbstractServlet {
    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        String userId = request.getParam(USER_ID_KEY);
        String password = request.getParam(USER_PASSWORD_KEY);
        if (login(userId, password)) {
            setLoginInfo(request, response, true);
            response.sendRedirect("/index.html");
            return;
        }
        setLoginInfo(request, response, false);
        response.sendRedirect("/user/login_failed.html");
    }

    private void setLoginInfo(HttpRequest request, HttpResponse response, boolean isLogin) {
        String loginValue = isLogin ? "true" : "false";
        HttpSession session = request.getSession();
        session.setAttribute("logined", loginValue);
        response.setCookie("sessionId", session.getId());
    }

    private boolean login(String userId, String password) {
        User user = DataBase.findUserById(userId);
        return (user != null) && user.matchPassword(password);
    }
}
