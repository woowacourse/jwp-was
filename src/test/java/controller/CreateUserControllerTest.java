package controller;

import db.DataBase;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.request.HttpRequest;
import web.response.HttpResponse;

import java.io.*;

import static controller.AbstractControllerTest.TEST_DIRECTORY;

public class CreateUserControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserControllerTest.class);

    @DisplayName("POST로 CreateUserController에 요청시, 유저가 생성되고 302 FOUD가 반환된다.")
    @Test
    void serviceUserControllerPostTest() {
        Controller controller = new CreateUserController();
        try {
            InputStream inputStream = new FileInputStream(new File(TEST_DIRECTORY + "HTTP_CREATE_USER.txt"));
            HttpRequest request = new HttpRequest(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HttpResponse response = new HttpResponse(outputStream);

            controller.service(request, response);

            //DB에 정보가 저장되었는지 확인한다
            User user = DataBase.findUserById(request.getRequestBodyByKey("userId"));
            Assertions.assertThat(user.getUserId()).isEqualTo(request.getRequestBodyByKey("userId"));
            Assertions.assertThat(user.getEmail()).isEqualTo(request.getRequestBodyByKey("email"));
            Assertions.assertThat(user.getName()).isEqualTo(request.getRequestBodyByKey("name"));
            Assertions.assertThat(user.getPassword()).isEqualTo(request.getRequestBodyByKey("password"));

            //올바른 response가 왔는지 확인한다
            String result = outputStream.toString();

            Assertions.assertThat(result).contains("HTTP/1.1 302 FOUND");
            Assertions.assertThat(result).contains("Location: /index.html");
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }
}
