package http.controller;

import http.common.Cookie;
import http.common.HttpStatus;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void 로그인_상태가_아니라면_login_페이지로_리다이렉트한다() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "not_Logined_Http_Header.txt"));
        HttpRequest request = HttpRequestFactory.createHttpRequest(in);

        HttpResponse response = new HttpResponse();
        UserListController controller = new UserListController();
        controller.service(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getCookie("logined")).isEqualTo(new Cookie("logined","false"));
    }

    @Test
    void 쿠기가_앖디면_login_페이지로_리다이렉트한다() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "cookie_notExist_Http_Header.txt"));
        HttpRequest request = HttpRequestFactory.createHttpRequest(in);

        HttpResponse response = new HttpResponse();
        UserListController controller = new UserListController();
        controller.service(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getCookie("logined")).isEqualTo(new Cookie("logined","false"));
    }

    @Test
    void 로그인된_유저가_요청시_유저_목록이_나오는지_확인한다() {

    }
}