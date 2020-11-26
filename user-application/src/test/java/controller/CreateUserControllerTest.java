package controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.controller.Controller;

public class CreateUserControllerTest extends ControllerTest {

    @DisplayName("CreateUserController doPost")
    @Test
    void doPost() throws IOException {
        String fileName = "Http_Request_POST_Create_User.txt";
        Controller controller = new CreateUserController();

        String body = service(fileName, controller);

        Assertions.assertAll(
            () -> assertThat(body).contains("HTTP/1.1 302 Found"),
            () -> assertThat(body).contains("Location: /index.html")
        );
    }
}
