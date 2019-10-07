package http.application.controller;

import http.application.Service;
import http.application.service.LoginService;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.info("request is {}", httpRequest);

        Service loginService = new LoginService();
        loginService.execute(httpRequest, httpResponse);
    }
}
