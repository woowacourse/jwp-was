package webserver.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DefaultControllerTest extends ControllerTest {

    @DisplayName("DefaultController doGet")
    @Test
    void doGet() throws IOException {
        String fileName = "Http_Request_GET_Index.txt";
        Controller controller = DefaultController.getInstance();

        String body = service(fileName, controller);

        assertThat(body).contains("HTTP/1.1 200 Ok");
    }
}
