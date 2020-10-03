package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import response.StatusCode;

public class UserController extends AbstractController {

    private HttpResponse createUser(HttpRequest request) {
        validateUriPath(request.getUriPath());
        User user = new User(
            request.getValueFromFormData("userId"),
            request.getValueFromFormData("password"),
            request.getValueFromFormData("name"),
            request.getValueFromFormData("email")
        );
        DataBase.addUser(user);

        return new HttpResponse(StatusCode.FOUND, "/");
    }

    private void validateUriPath(String uriPath) {
        if (!uriPath.equals(UriPathConstants.USER_CREATE_URI_PATH)) {
            throw new WrongUriException("bad request: strange uri");
        }
    }

    @Override
    protected HttpResponse doPost(HttpRequest httpRequest) {
        return createUser(httpRequest);
    }
}
