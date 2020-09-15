package controller;

import service.UserService;
import webserver.FileResponse;
import webserver.HttpStatus;
import webserver.Request;
import webserver.Response;

public class UserController {

    public static Response getCreateUser(Request request) {
        String input = request.extractQueryString();
        UserService.createUser(input);
        return Response.withFileResponse(HttpStatus.OK, new FileResponse("./templates/index.html", "text/html"));
    }

    public static Response postCreateUser(Request request) {
        String input = request.extractBody();
        UserService.createUser(input);
        return Response.withLocation(HttpStatus.FOUND, "/index.html");

    }
}
