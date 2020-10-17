package controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Stream;
import model.general.Header;
import model.request.HttpRequest;
import model.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ControllerMapper;

public class UserControllerTest {

    @Test
    @DisplayName("UserController 테스트")
    void executeApiServiceOperation() throws IOException {
        String filePath = "src/test/resources/input/post_api_request.txt";
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        Controller controller = ControllerMapper.selectController(httpRequest);
        HttpResponse httpResponse = controller.service(httpRequest);
        Map<Header, String> headers = httpResponse.getHeaders();

        Stream.of(
            assertThat(httpResponse).isInstanceOf(HttpResponse.class),
            assertThat(httpResponse.getBody()).isEqualTo(null),
            assertThat(headers.get(Header.LOCATION)).isEqualTo("/index.html"),
            assertThat(httpResponse.getHttpVersion()).isEqualTo("HTTP/1.1"),
            assertThat(httpResponse.getStatusCode()).isEqualTo("302"),
            assertThat(httpResponse.getReasonPhrase()).isEqualTo("Found")
        );
    }
}
