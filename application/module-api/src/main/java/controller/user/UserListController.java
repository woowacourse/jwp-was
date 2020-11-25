package controller.user;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import annotations.Controller;
import annotations.RequestMapping;
import controller.AbstractController;
import domain.HttpHeader;
import domain.HttpMethod;
import domain.HttpRequest;
import domain.HttpResponse;
import domain.HttpStatus;
import service.user.UserService;
import user.User;

@Controller
public class UserListController extends AbstractController {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserListController() {
        userService = UserService.getInstance();
        objectMapper = new ObjectMapper();
    }

    public UserListController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @RequestMapping(path = "/user/list", method = HttpMethod.GET)
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (httpRequest.notAuthorized()) {
            httpResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
            httpResponse.sendRedirect("/user/login.html");
            return;
        }
        List<User> list = userService.list();
        byte[] bytes = objectMapper.writeValueAsBytes(list);
        httpResponse.setHttpStatus(HttpStatus.OK);
        httpResponse.addHeader(HttpHeader.CONTENT_TYPE, "application/json;charset=utf-8");
        httpResponse.forward(bytes);
    }
}
