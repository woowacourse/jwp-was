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
    @DisplayName("UserController")
    void service() throws IOException {
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

    @Test
    @DisplayName("UserController - 지원하지 않는 메소드")
    void service_WhenInvalidMethod_Return405Response() throws IOException {
        String filePath = "src/test/resources/input/post_api_request_invalid_method.txt";
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        Controller controller = ControllerMapper.selectController(httpRequest);
        HttpResponse httpResponse = controller.service(httpRequest);

        Stream.of(
            assertThat(httpResponse).isInstanceOf(HttpResponse.class),
            assertThat(httpResponse.getBody()).isEqualTo(null),
            assertThat(httpResponse.getHttpVersion()).isEqualTo("HTTP/1.1"),
            assertThat(httpResponse.getStatusCode()).isEqualTo("405"),
            assertThat(httpResponse.getReasonPhrase()).isEqualTo("Method Not Allowed")
        );
    }

    @Test
    @DisplayName("UserController - 지원하지 않는 API")
    void service_WhenInvalidApi_Return405Response() throws IOException {
        String filePath = "src/test/resources/input/post_api_request_invalid_service.txt";
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        Controller controller = ControllerMapper.selectController(httpRequest);
        HttpResponse httpResponse = controller.service(httpRequest);

        Stream.of(
            assertThat(httpResponse).isInstanceOf(HttpResponse.class),
            assertThat(httpResponse.getBody()).isEqualTo(null),
            assertThat(httpResponse.getHttpVersion()).isEqualTo("HTTP/1.1"),
            assertThat(httpResponse.getStatusCode()).isEqualTo("404"),
            assertThat(httpResponse.getReasonPhrase()).isEqualTo("Not Found")
        );
    }
}
