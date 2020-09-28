package domain.user.controller;

import domain.user.service.UserService;
import webserver.request.Request;
import webserver.response.FileResponse;
import webserver.response.Response;
import webserver.response.Status;

import java.util.Map;

public class UserController {

    public static Response getCreateUser(Request request) {
        Map<String, String> inputs = Request.extractQueryString(request.getQueryString());
        UserService.createUser(inputs);
        return Response.withFileResponse(Status.OK, new FileResponse("./templates/index.html", "text/html"));
    }

    public static Response postCreateUser(Request request) {
        Map<String, String> inputs = Request.extractQueryString(request.getBody());
        UserService.createUser(inputs);
        return Response.withLocation(Status.FOUND, "/index.html");
    }
}
