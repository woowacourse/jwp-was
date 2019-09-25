package controller;

import db.DataBase;
import http.common.HttpHeader;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpUriParser;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.Response302;
import http.response.StatusLine;
import model.User;

import static controller.IndexController.INDEX_PATH;

public class UserCreateController implements Controller {

    private static final String USER_CREATE_PATH = "/user/create";
    private static final RequestMapping USER_CREATE_REQUEST_MAPPING = RequestMapping.of(HttpMethod.POST, HttpUriParser.parse(USER_CREATE_PATH));

    @Override
    public HttpResponse service(final HttpRequest httpRequest) {
        String userId = httpRequest.findUriParam("userId");
        String password = httpRequest.findUriParam("password");
        String name = httpRequest.findUriParam("name");
        String email = httpRequest.findUriParam("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        StatusLine statusLine = new StatusLine(httpRequest.getHttpVersion(), HttpStatus.FOUND);
        HttpHeader responseHeader = new HttpHeader();
        responseHeader.putHeader("Location", INDEX_PATH);

        return new Response302(statusLine, responseHeader, null);
    }

    @Override
    public boolean isMapping(final RequestMapping requestMapping) {
        return USER_CREATE_REQUEST_MAPPING.equals(requestMapping);
    }
}
