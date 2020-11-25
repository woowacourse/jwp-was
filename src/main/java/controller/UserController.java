package controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import annotation.RequestMapping;
import dto.JoinRequestDto;
import dto.UserResponseDto;
import http.ContentType;
import http.HttpBody;
import http.HttpSession;
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
        HttpSession session = httpRequest.getSession();
        String logined = String.valueOf(session.getAttribute("logined"));
        if (Objects.isNull(logined) || !"true".equals(logined)) {
            unauthorized(httpResponse);
            return;
        }

        List<UserResponseDto> users = userService.findAll();

        try {
            String page = View.render("user/list", users);
            httpResponse.setStatus(HttpStatus.OK);
            httpResponse.setBody(page.getBytes(), ContentType.HTML);
        } catch (IOException e) {
            httpResponse.internalServerError();
        }
    }

    private void unauthorized(HttpResponse httpResponse) {
        httpResponse.setStatus(HttpStatus.MOVED_PERMANENTLY);
        httpResponse.addHeader("Location", "/user/login.html");
    }
}
