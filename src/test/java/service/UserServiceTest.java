package service;

import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.stream.Stream;
import model.domain.User;
import model.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    @Test
    @DisplayName("Uer Service 동작 테스트")
    void execute() throws IOException {
        String filePath = "src/test/resources/input/post_api_request.txt";
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        UserService userService = UserService.getInstance();
        userService.createUser(httpRequest);

        Collection<User> users = DataBase.findAll();
        User user = DataBase.findUserById("javajigi");

        Stream.of(
            assertThat(users).hasSize(1),
            assertThat(user.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1"),
            assertThat(user.getUserId()).isEqualTo("javajigi")
        );
    }
}
