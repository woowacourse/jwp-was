package controller;

import db.DataBase;
import http.common.HttpHeader;
import http.cookie.Cookie;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.request.RequestLine;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static http.request.HttpRequest.SESSIONID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.StringUtils.BLANK;

class UserListControllerTest extends AbstractControllerTest {
    private UserListController userListController = UserListController.getInstance();
    private User signUpUser = new User("signUpId", "password", "name", "email");

    @BeforeEach
    void setUp() {
        signUp(signUpUser);
    }

    @Test
    void 로그인후_요청시_200_정상_응답() {
        HttpHeader requestHeader = new HttpHeader();
        requestHeader.addCookie(new Cookie.Builder(SESSIONID, getLoginSessionId(signUpUser.getUserId(), signUpUser.getPassword())).build());
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("GET /user/list HTTP/1.1"),
                requestHeader,
                new RequestBody(BLANK, BLANK));
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        ModelAndView modelAndView = userListController.doGet(httpRequest, httpResponse);

        assertEquals(modelAndView.getViewName(), "/user/list");
        assertEquals(modelAndView.getModelMap(), Collections.singletonMap("users", DataBase.findAll()));
    }

    @Test
    void 로그아웃_상태로_요청시_302_응답() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("GET /user/list HTTP/1.1"),
                new HttpHeader(),
                new RequestBody(BLANK, BLANK));
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        ModelAndView modelAndView = userListController.doGet(httpRequest, httpResponse);

        assertEquals(modelAndView.getViewName(), "redirect: /");
        assertEquals(modelAndView.getModelMap(), Collections.emptyMap());
    }

}