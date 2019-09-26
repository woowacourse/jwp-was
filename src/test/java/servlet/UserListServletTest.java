package servlet;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;
import test.HttpTestClient;
import webserver.http.Cookies;
import webserver.http.HttpHeaders;
import webserver.http.HttpStatus;

class UserListServletTest {
    private HttpTestClient httpTestClient = new HttpTestClient(HttpTestClient.DEFAULT_PORT);

    @Test
    void 로그인_상태로_요청() {
        // given
        DataBase.addUser(new User("bedi", "password", "bedi", "bedi@gmail.com"));

        final String jSessionId = httpTestClient.post().uri("/user/login")
                .body("userId=bedi&password=password")
                .exchange()
                .getCookie(Cookies.JSESSIONID);

        // when & then
        httpTestClient.get().uri("/user/list")
                .addCookie(Cookies.JSESSIONID, jSessionId)
                .exchange()
                .matchHttpStatus(HttpStatus.OK);
    }

    @Test
    void 로그아웃_상태로_요청() {
        // when & then
        httpTestClient.get().uri("/user/list")
                .exchange()
                .matchHttpStatus(HttpStatus.FOUND)
                .matchHeader(HttpHeaders.LOCATION, "/user/login");
    }
}