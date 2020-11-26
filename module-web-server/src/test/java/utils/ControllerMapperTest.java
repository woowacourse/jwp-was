package utils;

import static org.assertj.core.api.Assertions.assertThat;

import controller.Controller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import model.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ControllerMapperTest {

    @ParameterizedTest
    @DisplayName("Controller 선택")
    @ValueSource(strings = {
        "src/test/resources/input/get_template_file_request.txt",
        "src/test/resources/input/get_static_file_request.txt",
        "src/test/resources/input/get_api_request.txt",
        "src/test/resources/input/post_api_request.txt",
        "src/test/resources/input/post_api_request_invalid_service.txt"
    })
    void getStatusCode(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        ControllerMapper controllerMapper = new ControllerMapper(Collections.emptyMap());
        Controller controller = controllerMapper.selectController(httpRequest);

        assertThat(controller).isInstanceOf(Controller.class);
    }
}