package was.controller;

import db.DataBase;
import was.model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.QueryStringParams;
import webserver.http.response.HttpResponse;

public class LoginUserController extends AbstractController {
    private LoginUserController() {}

    public static LoginUserController getInstance() {
        return LoginUserController.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final LoginUserController INSTANCE = new LoginUserController();
    }

    @Override
    void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        QueryStringParams httpsParameters = httpRequest.getQueryStringParams();

        httpRequest.addHeader("Cookie", "logined=true");

        User user = new User(
                httpsParameters.get("userId"),
                httpsParameters.get("password"),
                httpsParameters.get("name"),
                httpsParameters.get("email")
        );

        if (DataBase.findAll().contains(user)) {
            httpResponse.addHeader("Set-Cookie", httpRequest.getHttpHeader().get("Cookie") + "; Path=/");
            httpResponse.redirect("/index.html");
            return;
        }

        httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
        httpResponse.redirect("/user/login_failed.html");
    }
}
