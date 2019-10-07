package http.application.controller;

import http.application.Service;
import http.application.service.CreateUserService;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.info("request is {}", httpRequest);

        Service createUserService = new CreateUserService();
        createUserService.execute(httpRequest, httpResponse);
    }
}
