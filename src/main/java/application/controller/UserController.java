package application.controller;

import application.db.DataBase;
import controller.AbstractController;
import controller.WrongRequestException;
import model.User;
import request.HttpRequest;
import response.Cookies;
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
        String userId = request.getValueFromFormData("userId");
        String password = request.getValueFromFormData("password");

        try {
            User user = DataBase.findUserById(userId);

            if (!user.getPassword().equals(password)) {
                throw new WrongUserIdPasswordException("userId is exist but password is wrong.");
            }
            return new HttpResponse(StatusCode.FOUND, "/")
                .setCookies(Cookies.createWithSingleCookie("login", "true"));
        } catch (WrongUserIdPasswordException e) {
            return new HttpResponse(StatusCode.FOUND, "/user/login_failed.html")
                .setCookies(Cookies.createWithSingleCookie("login", "false"));
        }
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
