package controller;

import controller.exception.MethodNotAllowedException;
import db.DataBase;
import http.common.HttpHeader;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.request.RequestLine;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.StringUtils.BLANK;

class UserControllerTest extends AbstractControllerTest {
    private UserController userController = UserController.getInstance();

    @Test
    void 유저생성() {
        User user = new User("olaf", "bmo", "bhy", "test@gmail.com");
        ModelAndView modelAndView = signUp(user);

        assertEquals(DataBase.findUserById(user.getUserId()), user);
        assertEquals(modelAndView.getViewName(), "redirect: /");
        assertEquals(modelAndView.getModelMap(), Collections.emptyMap());
    }

    @Test
    void doGetTest() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("GET /user/create HTTP/1.1"),
                new HttpHeader(),
                new RequestBody(BLANK, BLANK));
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        assertThrows(MethodNotAllowedException.class, () ->
                userController.doGet(httpRequest, httpResponse));
    }
}