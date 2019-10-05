package http.controller;

import db.DataBase;
import http.model.*;
import model.User;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

public class SignUpController implements Controller {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        User user = new User(httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"));

        DataBase.addUser(user);

        return new HttpResponse.Builder()
                .sendRedirect("/index.html")
                .protocols(HttpProtocols.HTTP1_1)
                .status(HttpStatus.FOUND)
                .addHeader(CONTENT_TYPE, ContentType.HTML.getType())
                .build();
    }
}