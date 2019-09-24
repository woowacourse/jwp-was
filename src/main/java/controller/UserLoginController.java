package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.ResponseMetaData;

import java.io.IOException;

public class UserLoginController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        String userId = request.findRequestBodyParam("userId");
        String password = request.findRequestBodyParam("password");

        log.debug("login request : userId={}", userId);

        ResponseMetaData responseMetaData = buildFailedResponseMetaData(request);

        if (DataBase.hasUser(userId)) {
            User user = DataBase.findUserById(userId);

            if (user.matchPassword(password)) {
                log.debug("login success : userId={}", userId);
                responseMetaData = buildSuccessfulResponseMetaData(request);
            }
        }

        response.setResponseMetaData(responseMetaData);
        doPost(request, response);
    }

    private ResponseMetaData buildFailedResponseMetaData(final HttpRequest request) {
        return ResponseMetaData.Builder
                .builder(request, HttpStatus.FOUND)
                .setCookie("logined=false")
                .location("/user/login_failed.html")
                .build();
    }

    private ResponseMetaData buildSuccessfulResponseMetaData(final HttpRequest request) {
        final ResponseMetaData responseMetaData;
        responseMetaData = ResponseMetaData.Builder
                .builder(request, HttpStatus.FOUND)
                .setCookie("logined=true", "/")
                .location("/index.html")
                .build();
        return responseMetaData;
    }
}
