package webserver.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ErrorControllerTest extends ControllerTest {

    @DisplayName("ErrorController doGet")
    @Test
    void doGet() throws IOException {
        String fileName = "Http_Request_GET_Not_Found.txt";
        Controller controller = ErrorController.getInstance();

        String body = service(fileName, controller);

        assertThat(body).contains("HTTP/1.1 404 Not Found");
    }

    @DisplayName("ErrorController doPost")
    @Test
    void doPost() throws IOException {
        String fileName = "Http_Request_POST_Not_Found.txt";
        Controller controller = ErrorController.getInstance();

        String body = service(fileName, controller);

        assertThat(body).contains("HTTP/1.1 404 Not Found");
    }
}
