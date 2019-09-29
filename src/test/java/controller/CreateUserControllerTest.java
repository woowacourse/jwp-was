package controller;

import controller.controllermapper.ControllerFactory;
import db.DataBase;
import http.request.Request;
import http.request.RequestUrl;
import http.response.Response;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserControllerTest extends BaseTest {
    private ControllerFactory factory = new ControllerFactory();

    @Test
    @DisplayName("POST /user/create 요청 보낼 시 RedirectResponse 생성 확인")
    void createPostUserResponse() throws IOException, URISyntaxException {
        User user = new User("javajigi", "password", "이인권", "podo@gmail.com");

        RequestUrl url = RequestUrl.from("/user/create");
        List<String> headerValues = Arrays.asList("POST /user/create HTTP/1.1",
                "59", "application/x-www-form-urlencoded",
                "userId=javajigi&password=password&name=이인권&email=podo@gmail.com");

        Request request = createPostRequest(url, headerValues);
        Response response = new Response();
        Controller controller = factory.mappingController(request);
        controller.processResponse(request, response);

        assertThat(DataBase.findUserById("javajigi")).isEqualTo(new User("javajigi", "password", "이인권", "podo@gmail.com"));
        DataBase.deleteUser(user);
    }
}
