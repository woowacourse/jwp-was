package http.controller;

import db.DataBase;
import http.model.*;
import model.User;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.HttpHeaders.SET_COOKIE;

public class LoginController implements Controller {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        HttpParameters httpParameters = httpRequest.getParameters();

        String userId = httpParameters.getParameter("userId");
        String password = httpParameters.getParameter("password");

        User user = DataBase.findUserById(userId);

        if (!(user == null) && user.isValidPassword(password)) {
            HttpSession httpSession = httpRequest.getHttpSession();
            httpSession.setAttributes("user", user);

            return new HttpResponse.Builder()
                    .sendRedirect("/index.html")
                    .protocols(HttpProtocols.HTTP1_1)
                    .status(HttpStatus.FOUND)
                    .addHeader(CONTENT_TYPE, ContentType.HTML.getType())
                    .addHeader(SET_COOKIE, "JSESSIONID=" + httpSession.getId() + "; Path=/")
                    .build();
        }
        return new HttpResponse.Builder()
                .sendRedirect("/user/login_failed.html")
                .protocols(HttpProtocols.HTTP1_1)
                .status(HttpStatus.FOUND)
                .addHeader(CONTENT_TYPE, ContentType.HTML.getType())
                .build();
    }
}
