package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import view.RedirectView;
import view.View;

public class LoginUserController extends AbstractController {

    @Override
    public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User loginUser = DataBase.findUserById(httpRequest.getQueryParameter("userId"));
        if (loginUser == null || loginUser.canLogin(httpRequest.getHeaderAttribute("password"))) {
            httpResponse.addHeader("Set-Cookie", "logined=false");
            return new RedirectView("user/login_failed.html");
        }

        httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
        return new RedirectView("index.html");
    }
}
