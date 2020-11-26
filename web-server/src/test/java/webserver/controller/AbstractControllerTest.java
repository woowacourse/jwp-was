package webserver.controller;

import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AbstractControllerTest extends ControllerTest {

    @DisplayName("AbstractController Method Not Allowed")
    @Test
    void service_MethodNotAllowed_ErrorHTML() throws IOException {
        String fileName = "Http_Request_TRACE.txt";
        Controller controller = DefaultController.getInstance();

        String body = service(fileName, controller);

        Assertions.assertThat(body).contains("HTTP/1.1 405 Method Not Allowed");
    }

    @DisplayName("AbstractController Not Implemented")
    @Test
    void service_NotImplemented_ErrorHTML() throws IOException {
        String fileName = "Http_Request_NULL.txt";
        Controller controller = DefaultController.getInstance();

        String body = service(fileName, controller);

        Assertions.assertThat(body).contains("HTTP/1.1 501 Not Implemented");
    }
}
