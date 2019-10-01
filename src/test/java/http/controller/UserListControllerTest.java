package http.controller;

import db.DataBase;
import http.common.HttpStatus;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponse;
import model.user.User;
import org.junit.jupiter.api.Test;

import java.io.*;

import static http.common.HttpStatus.NOT_FOUND;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void 쿠키가_앖디면_login_페이지로_리다이렉트한다() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "cookie_notExist_Http_Header.txt"));
        HttpRequest request = HttpRequestFactory.createHttpRequest(in);

        HttpResponse response = new HttpResponse(request);
        UserListController controller = new UserListController();
        controller.service(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/user/login.html");
    }

    @Test
    void 로그인된_유저가_요청시_유저_목록이_나오는지_확인한다() throws IOException {
        String userListRequest = "GET /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "Cookie: sessionId=";

        DataBase.addUser(new User("van", "1234", "van", "asd@asd.asd"));
        DataBase.addUser(new User("van123", "1234", "van1234", "asd@asd.asd"));
        //로그인을 진행한다.
        InputStream in = new FileInputStream(new File(testDirectory + "login_Http_Header.txt"));
        HttpRequest request = HttpRequestFactory.createHttpRequest(in);
        HttpResponse response = new HttpResponse(request);
        UserLoginController loginController = new UserLoginController();
        loginController.service(request, response);
        response.convert();
        String sessionId = response.getCookie("sessionId").getValue();
        userListRequest += sessionId + "\n";

        in = new ByteArrayInputStream(userListRequest.getBytes(UTF_8));

        HttpRequest listRequest = HttpRequestFactory.createHttpRequest(in);
        HttpResponse listResponse = new HttpResponse(request);

        UserListController controller = new UserListController();
        controller.service(listRequest, listResponse);

        assertThat(new String(listResponse.getBody())).contains("van");
        assertThat(new String(listResponse.getBody())).contains("van1234");
    }

    @Test
    void POST요청시_404에러를_발생한다() throws IOException {
        String userListRequest = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n";

        InputStream in = new ByteArrayInputStream(userListRequest.getBytes(UTF_8));

        HttpRequest request = HttpRequestFactory.createHttpRequest(in);
        HttpResponse response = new HttpResponse(request);

        UserListController controller = new UserListController();
        controller.service(request, response);

        assertThat(response.getStatus()).isEqualTo(NOT_FOUND);
    }
}