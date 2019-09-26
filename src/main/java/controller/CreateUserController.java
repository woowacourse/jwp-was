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

public class CreateUserController extends AbstractController {
    private final UserService userService;

    CreateUserController() {
        userService = UserService.getInstance();
    }

    private static void send300Response(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.writeBytes(httpResponse.doResponse());
        dos.flush();
    }

    @Override
    public void service(OutputStream out, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        doPost(httpRequest, httpResponse);
        send300Response(dos, httpResponse);
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doPost(httpRequest, httpResponse);
        userService.createUser(httpRequest);
        httpResponse.addStatus(ResponseStatus.of(302));
        httpResponse.addHeader(HttpHeaderField.LOCATION, DEFAULT_PAGE);
    }
}
