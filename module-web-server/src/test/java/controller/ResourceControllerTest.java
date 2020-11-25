package controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import model.general.Header;
import model.general.Headers;
import model.request.HttpRequest;
import model.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.ControllerMapper;

public class ResourceControllerTest {

    @ParameterizedTest
    @DisplayName("ResourceController - 자원 요청")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:text/html",
        "src/test/resources/input/get_static_file_request.txt:text/css"
    }, delimiter = ':')
    void service(String filePath, String contentType) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        Controller controller = ControllerMapper.selectController(httpRequest);
        HttpResponse httpResponse = controller.service(httpRequest);
        Headers headers = httpResponse.getHeaders();
        byte[] body = httpResponse.getBody();

        assertAll(
            () -> assertThat(httpResponse).isInstanceOf(HttpResponse.class),
            () -> assertThat(String.valueOf(body.length))
                .isEqualTo(headers.getValue(Header.CONTENT_LENGTH)),
            () -> assertThat(headers.getValue(Header.CONTENT_TYPE)).isEqualTo(contentType),
            () -> assertThat(httpResponse.getHttpVersion()).isEqualTo("HTTP/1.1"),
            () -> assertThat(httpResponse.getStatusCode()).isEqualTo("200"),
            () -> assertThat(httpResponse.getReasonPhrase()).isEqualTo("OK")
        );
    }

    @Test
    @DisplayName("ResourceController - 존재하지 않는 자원")
    void service_WhenInvalidResource_Return404Response() throws IOException {
        String filePath = "src/test/resources/input/get_template_file_request_invalid_file.txt";
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);
        Controller controller = ControllerMapper.selectController(httpRequest);
        HttpResponse httpResponse = controller.service(httpRequest);

        Headers headers = httpResponse.getHeaders();

        assertAll(
            () -> assertThat(httpResponse).isInstanceOf(HttpResponse.class),
            () -> assertThat(httpResponse.getBody()).isEqualTo(null),
            () -> assertThat(headers.getHeaders()
                .size()).isEqualTo(0),
            () -> assertThat(httpResponse.getHttpVersion()).isEqualTo("HTTP/1.1"),
            () -> assertThat(httpResponse.getStatusCode()).isEqualTo("404"),
            () -> assertThat(httpResponse.getReasonPhrase()).isEqualTo("Not Found")
        );
    }

    @Test
    @DisplayName("ResourceController - 지원하지 않는 메소드")
    void service_WhenInvalidMethod_Return405Response() throws IOException {
        String filePath = "src/test/resources/input/get_template_file_request_invalid_method.txt";
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);
        Controller controller = ControllerMapper.selectController(httpRequest);
        HttpResponse httpResponse = controller.service(httpRequest);

        Headers headers = httpResponse.getHeaders();

        assertAll(
            () -> assertThat(httpResponse).isInstanceOf(HttpResponse.class),
            () -> assertThat(httpResponse.getBody()).isEqualTo(null),
            () -> assertThat(headers.getHeaders()
                .size()).isEqualTo(0),
            () -> assertThat(httpResponse.getHttpVersion()).isEqualTo("HTTP/1.1"),
            () -> assertThat(httpResponse.getStatusCode()).isEqualTo("405"),
            () -> assertThat(httpResponse.getReasonPhrase()).isEqualTo("Method Not Allowed")
        );
    }

    @Test
    @DisplayName("ResourceController - 지원하지 않는 리소스")
    void service_WhenNotExistContentType_Return405Response() throws IOException {
        String filePath = "src/test/resources/input/get_template_file_request_invalid_content_type.txt";
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);
        Controller controller = new ResourceController();
        HttpResponse httpResponse = controller.service(httpRequest);

        Headers headers = httpResponse.getHeaders();

        assertAll(
            () -> assertThat(httpResponse).isInstanceOf(HttpResponse.class),
            () -> assertThat(httpResponse.getBody()).isEqualTo(null),
            () -> assertThat(headers.getHeaders()
                .size()).isEqualTo(0),
            () -> assertThat(httpResponse.getHttpVersion()).isEqualTo("HTTP/1.1"),
            () -> assertThat(httpResponse.getStatusCode()).isEqualTo("404"),
            () -> assertThat(httpResponse.getReasonPhrase()).isEqualTo("Not Found")
        );
    }

    @Test
    @DisplayName("ResourceController - 구현되지 않은 Api 요청")
    void service_WhenNotExistApi_Return405Response() throws IOException {
        String filePath = "src/test/resources/input/get_api_request.txt";
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);
        Controller controller = new ResourceController();
        HttpResponse httpResponse = controller.service(httpRequest);

        Headers headers = httpResponse.getHeaders();

        assertAll(
            () -> assertThat(httpResponse).isInstanceOf(HttpResponse.class),
            () -> assertThat(httpResponse.getBody()).isEqualTo(null),
            () -> assertThat(headers.getHeaders()
                .size()).isEqualTo(0),
            () -> assertThat(httpResponse.getHttpVersion()).isEqualTo("HTTP/1.1"),
            () -> assertThat(httpResponse.getStatusCode()).isEqualTo("405"),
            () -> assertThat(httpResponse.getReasonPhrase())
                .isEqualTo("Method Not Allowed")
        );
    }
}
