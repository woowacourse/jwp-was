package was.controller;

import db.DataBase;
import was.model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.QueryStringParams;
import webserver.http.response.HttpResponse;

public class LoginUserController extends AbstractController {
    private static final String COOKIE = "Cookie";
    private static final String SET_COOKIE = "Set-Cookie";

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

        User user = new User(
                httpsParameters.get("userId"),
                httpsParameters.get("password"),
                httpsParameters.get("name"),
                httpsParameters.get("email")
        );

        if (DataBase.findAll().contains(user)) {
            removeCookie(httpRequest);

            httpRequest.addHeader(COOKIE, "logined=true");
            httpResponse.addHeader(SET_COOKIE, httpRequest.getHttpHeader().get(COOKIE) + "; Path=/");
            httpResponse.redirect("/index.html");
            return;
        }

        httpResponse.addHeader(SET_COOKIE, "logined=false; Path=/");
        httpResponse.redirect("/user/login_failed.html");
    }

    private void removeCookie(final HttpRequest httpRequest) {
        if (httpRequest.getHttpHeader().getHeaders().containsKey(COOKIE)) {
            httpRequest.removeHeader(COOKIE);
        }
    }
}
