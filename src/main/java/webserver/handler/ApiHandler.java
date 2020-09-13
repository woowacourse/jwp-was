package webserver.handler;

import controller.ControllerAdvice;
import controller.UserController;
import db.DataBase;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import service.UserService;
import webserver.HttpMethod;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;
import webserver.utils.ResponseUtils;

public class ApiHandler {

    private final UserController userController;

    ApiHandler() {
        ControllerAdvice controllerAdvice = new ControllerAdvice();
        DataBase dataBase = new DataBase();
        UserService userService = new UserService(dataBase);
        this.userController = new UserController(controllerAdvice, userService);
    }

    public void handleAPI(OutputStream out, HttpRequest httpRequest) throws IOException {
        if (HttpMethod.GET.isSame(httpRequest.getHttpMethod())
            && httpRequest.getUrlPath().equals("/user/create")) {
            try (DataOutputStream dos = new DataOutputStream(out)) {
                HttpResponse user = userController.createUser(httpRequest);
                ResponseUtils.response(dos, user);
            }
        }
    }
}
