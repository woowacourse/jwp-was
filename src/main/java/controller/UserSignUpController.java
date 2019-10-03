package controller;

import db.DataBase;
import http.parser.HttpUriParser;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import static controller.IndexController.INDEX_PATH;

public class UserSignUpController implements Controller {

    private static final String USER_CREATE_PATH = "/user/create";
    private static final RequestMapping USER_CREATE_REQUEST_MAPPING = RequestMapping.of(HttpMethod.POST, HttpUriParser.parse(USER_CREATE_PATH));

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String userId = httpRequest.findBodyParam("userId");
        String password = httpRequest.findBodyParam("password");
        String name = httpRequest.findBodyParam("name");
        String email = httpRequest.findBodyParam("email");

        DataBase.addUser(new User(userId, password, name, email));

        httpResponse.redirect(INDEX_PATH);
    }

    @Override
    public boolean isMapping(final RequestMapping requestMapping) {
        return USER_CREATE_REQUEST_MAPPING.equals(requestMapping);
    }
}
