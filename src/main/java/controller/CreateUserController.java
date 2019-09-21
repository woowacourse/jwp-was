package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CreateUserController extends AbstractController {

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String requestBody = httpRequest.getHttpRequestBody().get(0);
        String[] tokens = requestBody.split("&");
        Map<String, String> parameters = new HashMap<>();

        Arrays.stream(tokens)
                .forEach(s -> parameters.put(s.split("=")[0], s.split("=")[1]));

        UserService userService = new UserService();
        userService.save(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
        httpResponse.sendRedirect("index.html");
    }
}
