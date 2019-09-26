package webserver.servlet;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeader;
import webserver.session.HttpSession;
import webserver.session.HttpSessionHelper;

public class LoginServlet extends RequestServlet {
    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        String loginId = httpRequest.getBody("userId");
        String loginPassword = httpRequest.getBody("password");
        User user = DataBase.findUserById(loginId);
        if (user != null && user.isMatchPassword(loginPassword)) {
            return loginSuccess(user);
        }
        return loginFail();
    }

    private HttpResponse loginSuccess(User user) {
        ResponseHeader header = new ResponseHeader();
        header.setCookie("user_session", getSessionId(user));
        header.setLocation("/index.html");
        return HttpResponse.found(header);
    }

    private String getSessionId(User user) {
        HttpSession userSession = HttpSessionHelper.create("user_session");
        userSession.setAttribute("userId", user.getUserId());
        return userSession.getId();
    }

    private HttpResponse loginFail() {
        ResponseHeader header = new ResponseHeader();
        header.setLocation("/user/login_failed.html");
        return HttpResponse.found(header);
    }
}
