package domain.user.controller;

import domain.user.service.UserService;
import utils.RequestUtils;
import webserver.FileResponse;
import webserver.HttpStatus;
import webserver.Request;
import webserver.Response;

import java.util.Map;

public class UserController {

    public static Response getCreateUser(Request request) {
        Map<String, String> inputs = RequestUtils.extractQueryString(request.getQueryString());
        UserService.createUser(inputs);
        return Response.withFileResponse(HttpStatus.OK, new FileResponse("./templates/index.html", "text/html"));
    }

    public static Response postCreateUser(Request request) {
        Map<String, String> inputs = RequestUtils.extractQueryString(request.getBody());
        UserService.createUser(inputs);
        return Response.withLocation(HttpStatus.FOUND, "/index.html");
    }
}
