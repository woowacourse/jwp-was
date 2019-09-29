package service;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.request.core.RequestLine;
import webserver.http.request.core.RequestMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.UtilData.*;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = UserService.getInstance();
    }

    @Test
    @DisplayName("유저 생성 테스트")
    void user_create_test() {
        RequestLine requestLine = new RequestLine(RequestMethod.of(GET_METHOD), REQUEST_GET_PARAM_PATH, REQUEST_VERSION);
        HttpRequest httpRequest = new HttpRequest(requestLine, GET_REQUEST_HEADER, QUERY_DATA);

        userService.createUser(httpRequest);

        assertThat(DataBase.findUserById("javajigi")).isEqualTo(new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net"));
    }

    @Test
    @DisplayName("유저 조회 테스트")
    void user_find_test() {
        RequestLine requestLine = new RequestLine(RequestMethod.of(GET_METHOD), REQUEST_GET_PARAM_PATH, REQUEST_VERSION);
        HttpRequest httpRequest = new HttpRequest(requestLine, GET_REQUEST_HEADER, QUERY_DATA);

        userService.createUser(httpRequest);

        assertThat(userService.getUser(httpRequest)).isEqualTo(new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net"));
    }

    @Test
    @DisplayName("유저 로그인 테스트")
    void user_login_test() {
        RequestLine requestLine = new RequestLine(RequestMethod.of(GET_METHOD), REQUEST_LOGIN_GET_PARAM_PATH, REQUEST_VERSION);
        HttpRequest httpRequest = new HttpRequest(requestLine, GET_REQUEST_HEADER, QUERY_DATA);

        userService.createUser(httpRequest);

        assertTrue(userService.loginUser(httpRequest));
    }
}
