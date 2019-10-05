package controller.custom;

import controller.BaseControllerTest;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;
import webserver.http.HttpSession;
import webserver.http.HttpStatus;
import webserver.http.ModelAndView;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest extends BaseControllerTest {
    private static final String TEST_DATA_DIRECTORY = "src/test/java/data";

    private String loginBody = "userId=sloth&password=password&name=sloth&email=sloth@slipp.net";
    private String loginRequest = "POST /user/login HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: " + loginBody.getBytes().length + "\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            loginBody;

    @Test
    void Login_success() {
        HttpRequest request = getDefaultHttpRequest(loginRequest.getBytes());
        HttpResponse response = HttpResponse.of(request.getHttpVersion());
        User signedUpUser = new User("sloth", "password", "sloth", "sloth@slipp.net");
        DataBase.addUser(signedUpUser);

        ModelAndView modelAndView = mapAndHandle(request, response);

        assertThat(modelAndView.getHttpStatus()).isEqualTo(HttpStatus.REDIRECT);
        assertThat(modelAndView.getView()).isEqualTo("/index.html");
        assertThat(response.getCookie("JSESSIONID")).isNotNull();
    }

    @Test
    void Login_fail() {
        HttpRequest request = getDefaultHttpRequest(loginRequest.getBytes());
        HttpResponse response = HttpResponse.of(request.getHttpVersion());
        User otherUser = new User("otherUser", "password", "Cheetah", "otherUser@slipp.net");
        DataBase.addUser(otherUser);

        ModelAndView modelAndView = mapAndHandle(request, response);

        assertThat(modelAndView.getHttpStatus()).isEqualTo(HttpStatus.REDIRECT);
        assertThat(modelAndView.getView()).isEqualTo("/user/login_failed.html");
        assertThat(response.getCookie("JSESSIONID")).isNull();
    }

    @Test
    void user_list_fail() {
        HttpRequest request = getDefaultHttpRequest(TEST_DATA_DIRECTORY + "/UserListHttpRequest.txt");
        HttpResponse response = HttpResponse.of(request.getHttpVersion());

        ModelAndView modelAndView = mapAndHandle(request, response);

        assertThat(modelAndView.getHttpStatus()).isEqualTo(HttpStatus.REDIRECT);
        assertThat(modelAndView.getView()).isEqualTo("/user/login.html");
    }

    @Test
    void user_list_success() {
        HttpRequest request = getDefaultHttpRequest(TEST_DATA_DIRECTORY + "/UserListHttpRequest.txt");
        HttpResponse response = HttpResponse.of(request.getHttpVersion());
        HttpSession session = request.getHttpSession();
        request.addHeader("Cookie", String.format("%s=%s", "JSESSIONID", session.getJSESSIONID()));
        session.setAttribute("user", new User("user", "user", "user", "user@user"));
        ModelAndView modelAndView = mapAndHandle(request, response);

        assertThat(modelAndView.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(modelAndView.getView()).isEqualTo("user/list");
    }

}