package controller;

import controller.core.AbstractController;
import service.UserService;
import webserver.http.HttpHeaderField;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.core.ResponseStatus;

import java.io.IOException;
import java.net.URISyntaxException;

public class CreateUserController extends AbstractController {
    private static final String DEFAULT_PATH = "/index.html";
    private final UserService userService;

    CreateUserController() {
        userService = UserService.getInstance();
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        doPost(httpRequest, httpResponse);
        httpResponse.sendResponse(httpRequest);
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        userService.createUser(httpRequest);
        httpResponse.addStatus(ResponseStatus.FOUND)
                .addHeader(HttpHeaderField.LOCATION, DEFAULT_PATH);
    }
}
