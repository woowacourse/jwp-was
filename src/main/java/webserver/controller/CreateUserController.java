package webserver.controller;

import java.io.DataOutputStream;
import java.io.IOException;

import application.service.UserService;
import webserver.exception.FailedRedirectException;
import webserver.exception.UnauthorizedRequestException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseGenerator;
import webserver.view.ModelAndView;

public class CreateUserController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, DataOutputStream dos) {
        try {
            UserService.saveUser(httpRequest.getHttpRequestBody());

            String redirectPath = "/index.html";
            ModelAndView modelAndView = new ModelAndView(redirectPath);

            HttpResponse httpResponse = HttpResponseGenerator.response302Header(modelAndView);

            if (!httpRequest.hasSessionId()) {
                String uuid = sessionManager.generateInitialSession();
                httpResponse.setInitialSession(uuid);
            }

            httpResponse.sendRedirect(dos);
        } catch (IOException e) {
            throw new FailedRedirectException();
        }
    }

    @Override
    public void doGet(HttpRequest httpRequest, DataOutputStream dos) {
        throw new UnauthorizedRequestException();
    }
}
