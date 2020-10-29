package controller;

import static org.assertj.core.api.Assertions.*;
import static test.IoUtil.*;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import application.Controller;
import domain.request.HttpServletSession;
import domain.request.HttpSessionStorage;
import domain.response.HttpServletResponse;
import servlet.HttpRequest;
import servlet.HttpResponse;
import servlet.HttpSession;
import util.HandlebarsTemplateEngine;

class ListControllerTest {

    @DisplayName("로그인 되어있지 않은 유저는 유저정보 조회시 Root로 Redirect 한다.")
    @Test
    void name() throws IOException {
        HttpRequest httpRequest = createRequest("list_controller/login_fail_request.txt");
        HttpResponse httpResponse = new HttpServletResponse(createOutputStream("/out/list_controller_actual.txt"));

        Controller ListController = new ListController(HandlebarsTemplateEngine.getInstance());
        ListController.service(httpRequest, httpResponse);

        String actual = readFile("/out/list_controller_actual.txt");

        assertThat(actual).contains("302 Found");
    }

    @DisplayName("로그인된 유저는 UserList페이지를 반환한다.")
    @Test
    void doGet() throws IOException {
        Map<String, HttpSession> sessionStorage = HttpSessionStorage.getInstance().getSessionStorage();
        HttpSession httpSession = new HttpServletSession("TEST_SESSION_ID");
        httpSession.setAttribute("logined", true);
        sessionStorage.put("TEST_SESSION_ID", httpSession);

        HttpRequest httpRequest = createRequest("list_controller/login_success_request.txt");
        HttpResponse httpResponse = new HttpServletResponse(createOutputStream("/out/login_success_response.txt"));

        Controller ListController = new ListController(HandlebarsTemplateEngine.getInstance());
        ListController.service(httpRequest, httpResponse);

        String actual = readFile("/out/login_success_response.txt");

        assertThat(actual).contains("200 OK");
    }
}