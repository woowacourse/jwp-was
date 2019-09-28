package service;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.request.core.RequestLine;
import webserver.http.request.core.RequestMethod;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UtilData.*;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = UserService.getInstance();
    }

    @Test
    @DisplayName("유저생성테스트")
    void user_service_test() {
        RequestLine requestLine = new RequestLine(RequestMethod.of(GET_METHOD), REQUEST_GET_PARAM_PATH, REQUEST_VERSION);
        HttpRequest httpRequest = new HttpRequest(requestLine, GET_REQUEST_HEADER, QUERY_DATA);

        userService.createUser(httpRequest);
        userService.createUser(httpRequest);

        assertThat(DataBase.findUserById("javajigi")).isEqualTo(new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net"));
    }
}
