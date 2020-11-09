package interfaces;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.User;
import web.controller.Controller;
import web.http.HttpRequest;
import web.http.HttpResponse;
import web.http.HttpStatus;
import web.session.HttpSession;
import web.session.SessionStorage;

public class UserListControllerTest {

    private String testDirectory = "./src/test/resources/";

    @DisplayName("로그인 후 사용자 목록 조회")
    @Test
    void listTest() throws IOException {
        User user = new User("javajigi", "123123", "pobi", "test@test.com");
        HttpSession session = SessionStorage.getSession("123123");
        session.setAttribute("user", user);

        InputStream in = new FileInputStream(new File(testDirectory + "Http_List.txt"));
        HttpRequest request = new HttpRequest(in);
        HttpResponse response = new HttpResponse(null);

        Controller userListController = new UserListController();
        userListController.service(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }
}
