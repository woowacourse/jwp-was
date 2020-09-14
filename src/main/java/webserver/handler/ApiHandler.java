package webserver.handler;

import controller.ControllerAdvice;
import controller.UserController;
import db.DataBase;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import service.UserService;
import webserver.HttpMethod;
import webserver.HttpStatusCode;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;
import webserver.utils.ResponseUtils;

public class ApiHandler {

    private final ControllerAdvice controllerAdvice;
    private final UserController userController;

    ApiHandler() {
        controllerAdvice = new ControllerAdvice();
        DataBase dataBase = new DataBase();
        UserService userService = new UserService(dataBase);
        this.userController = new UserController(controllerAdvice, userService);
    }

    public void handleAPI(OutputStream out, HttpRequest httpRequest) throws IOException {
        if ("/user/create".equals(httpRequest.getUrlPath())) {
            if (HttpMethod.POST.isSame(httpRequest.getHttpMethod())) {
                createUser(out, httpRequest);
                return;
            }
            response(out, httpRequest, HttpStatusCode.METHOD_NOT_ALLOW);
        }
        response(out, httpRequest, HttpStatusCode.NOT_FOUND);
    }

    private void createUser(OutputStream out, HttpRequest httpRequest) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            HttpResponse user = userController.createUser(httpRequest);
            ResponseUtils.response(dos, user);
        }
    }

    private void response(OutputStream out, HttpRequest httpRequest, HttpStatusCode httpStatusCode)
        throws IOException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            HttpResponse httpResponse
                = controllerAdvice.handleHttpStatusCode(httpRequest, httpStatusCode);
            ResponseUtils.response(dos, httpResponse);
        }
    }
}
