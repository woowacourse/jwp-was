package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import exception.UnauthorizedRequestException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseGenerator;
import service.UserService;
import utils.FileIoUtils;
import utils.ResourcePathUtils;

public class UserLoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, DataOutputStream dos) {
        UserService userService = new UserService();

        final Map<String, String> requestBody = httpRequest.getHttpRequestBody();
        final String userId = requestBody.get("userId");
        final String password = requestBody.get("password");
        boolean result = userService.login(userId, password);

        try {
            String location = (result) ? "/index.html" : "/user/login_failed.html";
            String path = ResourcePathUtils.getResourcePath(location);
            byte[] responseBody = FileIoUtils.loadFileFromClasspath(path);
            HttpResponse httpResponse;

            if (result) {
                String sessionId = httpRequest.getCookieValue();
                sessionManager.addSessionAttribute(sessionId, "userId", userId);
                httpResponse = HttpResponseGenerator.responseLoginSuccess(path, responseBody.length, sessionId);
                httpResponse.forward(responseBody, dos);
                return;
            }

            httpResponse = HttpResponseGenerator.response200Header(path, responseBody.length);
            httpResponse.forward(responseBody, dos);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpRequest httpRequest, DataOutputStream dos) {
        throw new UnauthorizedRequestException();
    }
}
