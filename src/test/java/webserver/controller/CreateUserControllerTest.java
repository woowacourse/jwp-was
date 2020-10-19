package webserver.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateUserControllerTest extends ControllerTest {

    @DisplayName("CreateUserController doPost")
    @Test
    void doPost() throws IOException {
        String fileName = "Http_Request_POST_Create_User.txt";
        Controller controller = new CreateUserController();

        String body = service(fileName, controller);

        assertAll(() -> {
            assertThat(body).contains("HTTP/1.1 302 Found");
            assertThat(body).contains("Location: /index.html");
        });
    }
}
