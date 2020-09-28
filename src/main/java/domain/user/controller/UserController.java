package domain.user.controller;

import domain.user.service.UserService;
import webserver.request.HttpRequest;
import webserver.response.FileResponse;
import webserver.response.HttpResponse;
import webserver.response.Status;

public class UserController {

    public static HttpResponse getCreateUser(HttpRequest httpRequest) {
        UserService.createUser(httpRequest);
        return HttpResponse.withFileResponse(Status.OK, new FileResponse("./templates/index.html", "text/html"));
    }

    public static HttpResponse postCreateUser(HttpRequest httpRequest) {
        UserService.createUser(httpRequest);
        return HttpResponse.withLocation(Status.FOUND, "/index.html");
    }
}
