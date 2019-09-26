package controller;

import db.DataBase;
import model.User;
import model.http.HttpRequest;
import model.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.HttpStatus;
import utils.RequestHeaderParser;
import utils.RequestDispatcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest {
    public static final String TEST_DATA_DIRECTORY = "src/test/java/data";

    private HttpRequest request;
    private HttpResponse response;
    private User loginTargetUser;
    private User otherUser;

    @BeforeEach
    void setUp() throws IOException {
        // 회원가입
        loginTargetUser = new User("javajigi", "password", "sloth", "javajigi@slipp.net");
        otherUser = new User("tiger", "tiger", "tiger", "tiger@sugar.haohe");

        request = RequestHeaderParser.parseRequest(
                new InputStreamReader(new FileInputStream(new File(TEST_DATA_DIRECTORY + "/LoginHttpRequest.txt"))));
        response = HttpResponse.of();
    }

    @Test
    void Login_success() {
        DataBase.addUser(loginTargetUser);
        response = RequestDispatcher.handle(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.REDIRECT);
    }

    @Test
    void Login_fail() {
        DataBase.addUser(otherUser);
        response = RequestDispatcher.handle(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}