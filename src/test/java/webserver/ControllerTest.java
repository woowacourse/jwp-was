package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControllerTest {

    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    void createUser() throws IOException {
        String input = "GET /user/create?userId=javajigi&password=password&name=jaesung&email=javajigi@slipp.net HTTP/1.1";
        Request request = new Request(new ByteArrayInputStream(input.getBytes()));
        Response response = controller.handle(request);
        assertThat(response.getPath()).isEqualTo("./templates/index.html");
    }
}
