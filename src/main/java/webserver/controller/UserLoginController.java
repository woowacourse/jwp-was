package webserver.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import application.service.UserService;
import webserver.exception.UnauthorizedRequestException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseGenerator;
import webserver.view.ModelAndView;

public class UserLoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, DataOutputStream dos) {
        UserService userService = new UserService();

        final Map<String, String> requestBody = httpRequest.getHttpRequestBody();
        final String userId = requestBody.get("userId");
        final String password = requestBody.get("password");
        boolean result = userService.login(userId, password);

        try {
            String location = (result) ? "/index.html" : "/login_failed.html";
            HttpResponse httpResponse;

            ModelAndView modelAndView = new ModelAndView(location);

            if (result && httpRequest.hasSessionId()) {
                String sessionId = httpRequest.getSessionId();
                sessionManager.addSessionAttribute(sessionId, "userId", userId);
                httpResponse = HttpResponseGenerator.responseLoginSuccess(modelAndView, sessionId);
                httpResponse.forward(dos);
                return;
            }

            httpResponse = HttpResponseGenerator.response200Header(modelAndView);

            httpResponse.forward(dos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpRequest httpRequest, DataOutputStream dos) {
        throw new UnauthorizedRequestException();
    }
}
