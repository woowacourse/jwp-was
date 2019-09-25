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
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest {
    public static final String TEST_DATA_DIRECTORY = "src/test/java/data";

    private HttpRequest successRequest;
    private HttpResponse response;
    private User loginTargetUser;
    private User otherUser;

    @BeforeEach
    void setUp() throws IOException {
        // 회원가입
        loginTargetUser = new User("sloth", "sloth", "sloth", "sloth@woowa.com");
        otherUser = new User("tiger", "tiger", "tiger", "tiger@sugar.haohe");

        successRequest = RequestHeaderParser.parseRequest(
                new InputStreamReader(new FileInputStream(new File(TEST_DATA_DIRECTORY + "/LoginHttpRequest.txt"))));
        response = HttpResponse.of();
    }

    @Test
    void Login_success() throws IOException, URISyntaxException {
        DataBase.addUser(loginTargetUser);
        response = RequestDispatcher.handle(successRequest, response);

        assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
