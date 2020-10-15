package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import response.StatusCode;

public class UserController extends AbstractController {

    public static final String CREATE_URI_PATH = "/user/create";
    public static final String LOGIN_URI_PATH = "/user/login";

    private HttpResponse create(HttpRequest request) {
        User user = new User(
            request.getValueFromFormData("userId"),
            request.getValueFromFormData("password"),
            request.getValueFromFormData("name"),
            request.getValueFromFormData("email")
        );
        DataBase.addUser(user);

        return new HttpResponse(StatusCode.FOUND, "/");
    }

    private HttpResponse login(HttpRequest request) {
        // todo 쿠키로 logined=true 내려주도록 구현

       return new HttpResponse(StatusCode.OK, "/");
    }

    @Override
    protected HttpResponse doPost(HttpRequest httpRequest) {
        if (httpRequest.getUriPath().equals(CREATE_URI_PATH)) {
            return create(httpRequest);
        }
        if (httpRequest.getUriPath().equals(LOGIN_URI_PATH)) {
            return login(httpRequest);
        }
        throw new WrongRequestException("uri that does not exist in the POST method.");
    }
}
