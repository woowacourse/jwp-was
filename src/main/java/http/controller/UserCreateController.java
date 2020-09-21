package http.controller;

import http.RequestBody;
import http.RequestHeader;
import http.ResponseHeader;
import service.UserService;

import java.io.DataOutputStream;

public class UserCreateController implements Controller {
    @Override
    public void get(DataOutputStream dos, RequestHeader requestHeader) {
        ResponseHeader.response405Header(dos);
    }

    @Override
    public void post(DataOutputStream dos, RequestHeader requestHeader, RequestBody requestBody) {
        UserService userService = new UserService();
        userService.createUser(requestBody);
        ResponseHeader.response302Header(dos, "/index.html");
    }
}
