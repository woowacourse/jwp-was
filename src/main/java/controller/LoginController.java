package controller;

import db.DataBase;
import model.User;
import utils.UrlEncodedParser;
import webserver.CookieLoginStatus;
import webserver.HttpMethod;
import webserver.Request;
import webserver.Response;

import java.util.HashMap;
import java.util.Map;

public class LoginController implements Controller {

    private static final String USER_LOGIN_URL = "/user/login";

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String LOGINED_COOKIE_KEY = "logined";

    private static Map<HttpMethod, HandleHttpMethod> methods;

    static {
        methods = new HashMap<>();
        methods.put(HttpMethod.POST, HandleHttpMethod.POST);
    }

    @Override
    public Response service(Request request) {
        return methods.get(request.getMethod()).method(request);
    }

    @Override
    public String getPath() {
        return USER_LOGIN_URL;
    }

    @Override
    public boolean isMapping(Request request) {
        return methods.containsKey(request.getMethod());
    }

    private enum HandleHttpMethod {
        POST {
            Response method(Request request) {
                Map<String, String> parsedBody = UrlEncodedParser.parse(new String(request.getBody()));
                User user = DataBase.findUserById(parsedBody.get(USER_ID));

                String redirectUrl = "/user/login_failed.html";
                String loginedCookie = CookieLoginStatus.False.getText();

                if (verify(user, parsedBody.get(PASSWORD))) {
                    redirectUrl = "/index.html";
                    loginedCookie = CookieLoginStatus.TRUE.getText();
                }

                return Response.ResponseBuilder.redirect(redirectUrl)
                        .withCookie(LOGINED_COOKIE_KEY, loginedCookie)
                        .build();
            }
        };

        private static boolean verify(User user, String password) {
            return user != null && user.matchPassword(password);
        }

        abstract Response method(Request request);
    }
}
