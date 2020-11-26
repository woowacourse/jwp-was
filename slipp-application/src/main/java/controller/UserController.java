package controller;

import java.io.IOException;
import java.util.List;

import annotation.RequestMapping;
import dto.JoinRequestDto;
import dto.UserResponseDto;
import http.ContentType;
import http.HttpBody;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import service.UserService;
import view.View;

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

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        validateLogin(httpRequest);

        List<UserResponseDto> users = userService.findAll();

        try {
            String page = View.render("user/list", users);
            httpResponse.setStatus(HttpStatus.OK);
            httpResponse.setBody(page.getBytes(), ContentType.HTML);
        } catch (IOException e) {
            httpResponse.internalServerError();
        }
    }
}
