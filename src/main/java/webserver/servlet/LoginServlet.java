package webserver.servlet;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeader;

public class LoginServlet extends RequestServlet {
    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        String loginId = httpRequest.getBody("userId");
        String loginPassword = httpRequest.getBody("password");
        User user = DataBase.findUserById(loginId);
        if (user != null && user.isMatchPassword(loginPassword)) {
            return loginSuccess();
        }
        return loginFail();
    }

    private HttpResponse loginSuccess() {
        return redirectWithLoginCookie("/index.html", true);
    }

    private HttpResponse loginFail() {
        return redirectWithLoginCookie("/user/login_failed.html", false);
    }

    private HttpResponse redirectWithLoginCookie(String url, boolean value) {
        ResponseHeader header = new ResponseHeader();
        header.setCookieLogined(value);
        header.setLocation(url);
        return HttpResponse.found(header);
    }
}
