package webserver.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import application.service.UserService;
import webserver.exception.FailedForwardException;
import webserver.exception.UnauthorizedRequestException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseGenerator;
import webserver.view.ModelAndView;

import static webserver.http.request.HttpRequestReader.REQUEST_URI;

public class UserListController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, DataOutputStream dos) {
        throw new UnauthorizedRequestException();
    }

    @Override
    public void doGet(HttpRequest httpRequest, DataOutputStream dos) {
        try {
            ModelAndView modelAndView;

            if (doseNotLogin(httpRequest)) {
                modelAndView = new ModelAndView("/user/login.html");
                HttpResponse httpResponse = HttpResponseGenerator.response302Header(modelAndView);
                httpResponse.sendRedirect(dos);
            }

            Map<String, Object> users = new LinkedHashMap<>();
            users.put("users", UserService.findAllUsers());

            String requestUri = httpRequest.getRequestLineElement(REQUEST_URI);
            modelAndView = new ModelAndView(requestUri + ".html", users);
            HttpResponse httpResponse = HttpResponseGenerator.response200Header(modelAndView);

            httpResponse.forward(dos);
        } catch (IOException e) {
            throw new FailedForwardException();
        }
    }

    private boolean doseNotLogin(HttpRequest httpRequest) {
        return !httpRequest.hasCookieValue("logined");
    }
}
