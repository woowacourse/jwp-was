package controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Stream;
import model.general.Header;
import model.request.Request;
import model.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ControllerTest {

    @Test
    @DisplayName("Controller 테스트 - 자원 요청")
    void executeResourceServiceOperation() throws IOException {
        String filePath = "src/test/resources/input/get_template_file_request.txt";
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);

        Response response = Controller.executeOperation(request);
        Map<Header, String> headers = response.getHeaders();
        byte[] body = response.getBody();

        Stream.of(
            assertThat(response).isInstanceOf(Response.class),
            assertThat(String.valueOf(body.length)).isEqualTo(headers.get(Header.CONTENT_LENGTH)),
            assertThat(headers.get(Header.CONTENT_TYPE)).isEqualTo("text/html"),
            assertThat(response.getHttpVersion()).isEqualTo("HTTP/1.1"),
            assertThat(response.getStatusCode()).isEqualTo("200"),
            assertThat(response.getReasonPhrase()).isEqualTo("OK")
        );
    }

    @Test
    @DisplayName("Controller 테스트 - API 요청")
    void executeApiServiceOperation() throws IOException {
        String filePath = "src/test/resources/input/post_api_request.txt";
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);

        Response response = Controller.executeOperation(request);
        Map<Header, String> headers = response.getHeaders();

        Stream.of(
            assertThat(response).isInstanceOf(Response.class),
            assertThat(response.getBody()).isEqualTo(null),
            assertThat(headers.get(Header.LOCATION)).isEqualTo("/index.html"),
            assertThat(response.getHttpVersion()).isEqualTo("HTTP/1.1"),
            assertThat(response.getStatusCode()).isEqualTo("302"),
            assertThat(response.getReasonPhrase()).isEqualTo("Found")
        );
    }

    @Test
    @DisplayName("Controller 테스트 - 500 Internal Error")
    void executeInvalidOperation() throws IOException {
        String filePath = "src/test/resources/input/get_invalid_template_file_request.txt";
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);

        Response response = Controller.executeOperation(request);
        Map<Header, String> headers = response.getHeaders();

        Stream.of(
            assertThat(response).isInstanceOf(Response.class),
            assertThat(response.getBody()).isEqualTo(null),
            assertThat(headers.size()).isEqualTo(0),
            assertThat(response.getHttpVersion()).isEqualTo("HTTP/1.1"),
            assertThat(response.getStatusCode()).isEqualTo("500"),
            assertThat(response.getReasonPhrase()).isEqualTo("Internal Server Error")
        );
    }
}
