package controller;

import db.DataBase;
import model.User;
import utils.UrlEncodedParser;
import webserver.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LoginController implements Controller {

    private static final String USER_LOGIN_URL = "/user/login";

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String LOGINED_COOKIE_KEY = "logined";

    private static Map<RequestMapping, HandleHttpMethod> methods;

    static {
        methods = new HashMap<>();
        methods.put(new RequestMapping(HttpMethod.POST, USER_LOGIN_URL), HandleHttpMethod.POST);
    }

    @Override
    public Response service(Request request) {
        return methods.get(request.getRequestMapping()).method(request);
    }

    @Override
    public Set<RequestMapping> getMethodKeys() {
        return methods.keySet();
    }

    @Override
    public boolean isMapping(Request request) {
        return methods.containsKey(request.getRequestMapping());
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
