package controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;
import model.request.HttpRequest;
import model.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ControllerMapper;

public class ErrorControllerTest {

    @Test
    @DisplayName("ErrorController")
    void service() throws IOException {
        String filePath = "src/test/resources/input/post_api_request_invalid_uri.txt";
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        Controller controller = ControllerMapper.selectController(httpRequest);
        HttpResponse httpResponse = controller.service(httpRequest);

        Stream.of(
            assertThat(controller).isInstanceOf(ErrorController.class),
            assertThat(httpResponse).isInstanceOf(HttpResponse.class),
            assertThat(httpResponse.getBody()).isEqualTo(null),
            assertThat(httpResponse.getHttpVersion()).isEqualTo("HTTP/1.1"),
            assertThat(httpResponse.getStatusCode()).isEqualTo("404"),
            assertThat(httpResponse.getReasonPhrase()).isEqualTo("Not Found")
        );
    }
}
