package controller;

import http.common.ContentType;
import http.common.HttpHeader;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.request.RequestLine;
import http.response.HttpResponse;
import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static http.common.ContentType.FORM_URLENCODED;

public abstract class AbstractControllerTest {
    ModelAndView signUp(User user) {
        Map<String, String> userData = new HashMap<>();
        userData.put("userId", user.getUserId());
        userData.put("password", user.getPassword());
        userData.put("name", user.getName());
        userData.put("email", user.getEmail());

        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("POST /user/create HTTP/1.1"),
                new HttpHeader(),
                new RequestBody(getQueryString(userData), ContentType.FORM_URLENCODED));
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        UserController userController = UserController.getInstance();
        return userController.doPost(httpRequest, httpResponse);
    }

    String getLoginSessionId(String id, String password) {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("userId", id);
        loginData.put("password", password);

        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("POST /user/login HTTP/1.1"),
                new HttpHeader(),
                new RequestBody(getQueryString(loginData), FORM_URLENCODED));
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        LoginController loginController = LoginController.getInstance();
        loginController.doPost(httpRequest, httpResponse);

        return httpRequest.getSession().getId();
    }

    String getQueryString(Map<String, String> data) {
        return data.keySet().stream()
                .map(key -> String.format("%s=%s", key, data.get(key)))
                .collect(Collectors.joining("&"));
    }
}
