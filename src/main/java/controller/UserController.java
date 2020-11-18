package controller;

import annotation.RequestMapping;
import dto.JoinRequestDto;
import http.HttpBody;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import service.UserService;

@RequestMapping(path = "/user")
public class UserController extends AbstractController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpBody httpBody = httpRequest.getBody();
        JoinRequestDto joinRequestDto = new JoinRequestDto(
            httpBody.get("userId"),
            httpBody.get("password"),
            httpBody.get("name"),
            httpBody.get("email"));
        userService.join(joinRequestDto);
        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.addHeader("Location", "/index.html");
    }
}
