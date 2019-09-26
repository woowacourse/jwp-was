package controller;

import db.DataBase;
import http.common.HttpHeader;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpUriParser;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.StatusLine;
import model.User;

import static controller.IndexController.INDEX_PATH;

public class UserCreateController implements Controller {

    private static final String USER_CREATE_PATH = "/user/create";
    private static final RequestMapping USER_CREATE_REQUEST_MAPPING = RequestMapping.of(HttpMethod.POST, HttpUriParser.parse(USER_CREATE_PATH));

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String userId = httpRequest.findBodyParam("userId");
        String password = httpRequest.findBodyParam("password");
        String name = httpRequest.findBodyParam("name");
        String email = httpRequest.findBodyParam("email");

        DataBase.addUser(new User(userId, password, name, email));

        StatusLine statusLine = new StatusLine(httpRequest.getHttpVersion(), HttpStatus.FOUND);
        HttpHeader responseHeader = new HttpHeader();
        responseHeader.putHeader("Location", INDEX_PATH);

        httpResponse.setStatusLine(statusLine);
        httpResponse.setResponseHeader(responseHeader);
    }

    @Override
    public boolean isMapping(final RequestMapping requestMapping) {
        return USER_CREATE_REQUEST_MAPPING.equals(requestMapping);
    }
}
