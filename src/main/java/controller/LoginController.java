package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import model.User;
import utils.FileIoUtils;

public class LoginController extends AbstractController {
    private static class LoginControllerLazyHolder {
        private static final LoginController INSTANCE = new LoginController();
    }

    public static LoginController getInstance() {
        return LoginControllerLazyHolder.INSTANCE;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setBody(FileIoUtils.loadFileFromClasspath("./templates" + "/user/login" + ".html"));
        httpResponse.setResponseStatus(ResponseStatus.OK);
        httpResponse.addHeaderAttribute("Content-Type", "text/html;charset=utf-8");
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getFormDataParameter("userId");
        String password = httpRequest.getFormDataParameter("password");

        User user = DataBase.findUserById(userId);
        if (user.matchPassword(password)) {
            httpResponse.setResponseStatus(ResponseStatus.FOUND);
            httpResponse.addHeaderAttribute("Location", "/");
            httpResponse.addHeaderAttribute("Set-Cookie", "logined=true; Path=/");
            return;
        }
        httpResponse.setResponseStatus(ResponseStatus.FOUND);
        httpResponse.addHeaderAttribute("Location", "/user/login_failed");
        httpResponse.addHeaderAttribute("Set-Cookie", "logined=false; Path=/");
    }
}
