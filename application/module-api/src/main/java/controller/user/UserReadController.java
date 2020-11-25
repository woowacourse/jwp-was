package controller.user;

import java.io.IOException;
import java.util.Objects;

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
public class UserReadController extends AbstractController {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserReadController() {
        userService = UserService.getInstance();
        objectMapper = new ObjectMapper();
    }

    public UserReadController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @RequestMapping(path = "/user/profile", method = HttpMethod.GET)
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        User user = userService.findByUserId(httpRequest.getParameter(User.USER_ID));
        if (Objects.isNull(user)) {
            validateParameter(httpRequest, httpResponse);
            httpResponse.error();
            return;
        }
        byte[] body = objectMapper.writeValueAsBytes(user);
        httpResponse.setHttpStatus(HttpStatus.OK);
        httpResponse.addHeader(HttpHeader.CONTENT_TYPE, "application/json;charset=UTF-8");
        httpResponse.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length));
        httpResponse.forward(body);
    }

    private void validateParameter(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!httpRequest.containsParameter(User.USER_ID)) {
            httpResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        }
        if (httpRequest.containsParameter(User.USER_ID)) {
            httpResponse.setHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
