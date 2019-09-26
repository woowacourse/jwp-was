package http.controller;

import db.DataBase;
import http.model.*;
import model.User;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

public class SignUpController implements Controller {
    private final static String TEMPLATES_PATH = "./templates";

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        HttpParameters httpParameters = httpRequest.getParameters();

        User user = new User(httpParameters.getParameter("userId"),
                httpParameters.getParameter("password"),
                httpParameters.getParameter("name"),
                httpParameters.getParameter("email"));

        DataBase.addUser(user);

        return new HttpResponse.Builder()
                .sendRedirect(TEMPLATES_PATH + "/index.html")
                .protocols(HttpProtocols.HTTP1_1)
                .status(HttpStatus.FOUND)
                .addHeader(CONTENT_TYPE, ContentType.HTML.getType())
                .build();
    }
}