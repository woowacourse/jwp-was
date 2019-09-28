package controller;

import controller.core.AbstractController;
import service.UserService;
import webserver.http.HttpHeaderField;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.core.ResponseStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class CreateUserController extends AbstractController {
    private final UserService userService;

    CreateUserController() {
        userService = UserService.getInstance();
    }

    @Override
    public void service(OutputStream out, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        DataOutputStream dos = new DataOutputStream(out);
        doPost(httpRequest, httpResponse);
        sendResponse(dos);
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doPost(httpRequest, httpResponse);
        userService.createUser(httpRequest);
        httpResponse.addStatus(ResponseStatus.of(302));
        httpResponse.addHeader(HttpHeaderField.LOCATION, DEFAULT_PAGE);
    }
}
