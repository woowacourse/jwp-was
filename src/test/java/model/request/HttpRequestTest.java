package model.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import model.general.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class HttpRequestTest {

    @ParameterizedTest
    @DisplayName("Request 생성 테스트")
    @ValueSource(strings = {
        "src/test/resources/input/get_template_file_request.txt",
        "src/test/resources/input/get_static_file_request.txt",
        "src/test/resources/input/get_api_request.txt",
        "src/test/resources/input/post_api_request.txt"
    })
    void create(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        assertThat(httpRequest).isInstanceOf(HttpRequest.class);
    }

    @ParameterizedTest
    @DisplayName("메소드 일치 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:GET",
        "src/test/resources/input/get_static_file_request.txt:GET",
        "src/test/resources/input/get_api_request.txt:GET",
        "src/test/resources/input/post_api_request.txt:POST"
    }, delimiter = ':')
    void isSameMethod(String filePath, String method) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        assertThat(httpRequest.isSameMethod(Method.of(method))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("Uri가 같은지 테스트")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:/index.html",
        "src/test/resources/input/get_static_file_request.txt:/css/styles.css",
        "src/test/resources/input/get_api_request.txt:/user/create",
        "src/test/resources/input/post_api_request.txt:/user/create"
    }, delimiter = ':')
    void isSameUri(String filePath, String uri) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        assertThat(httpRequest.isSameUri(uri)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("파라미터 확인")
    @MethodSource("provideParameters")
    void extractParameters(String filePath, Map<String, String> parameters) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);
        Map<String, String> requestParameters = httpRequest.extractParameters();

        assertThat(requestParameters).isEqualTo(parameters);
    }

    private static Stream<Arguments> provideParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("password", "password");
        parameters.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        parameters.put("userId", "javajigi");
        parameters.put("email", "javajigi%40slipp.net");

        return Stream.of(
            Arguments.of("src/test/resources/input/get_template_file_request.txt",
                Collections.emptyMap()),
            Arguments.of("src/test/resources/input/get_static_file_request.txt",
                Collections.emptyMap()),
            Arguments.of("src/test/resources/input/get_api_request.txt",
                parameters),
            Arguments.of("src/test/resources/input/post_api_request.txt",
                parameters)
        );
    }

    @ParameterizedTest
    @DisplayName("Method 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:GET",
        "src/test/resources/input/get_static_file_request.txt:GET",
        "src/test/resources/input/get_api_request.txt:GET",
        "src/test/resources/input/post_api_request.txt:POST",
        "src/test/resources/input/put_api_request.txt:PUT"
    }, delimiter = ':')
    void getMethod(String filePath, String method) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        assertThat(httpRequest.getMethod()).isEqualTo(Method.of(method));
    }

    @ParameterizedTest
    @DisplayName("requestUri 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:/index.html",
        "src/test/resources/input/get_static_file_request.txt:/css/styles.css",
        "src/test/resources/input/get_api_request.txt:/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",
        "src/test/resources/input/post_api_request.txt:/user/create"
    }, delimiter = ':')
    void getRequestUri(String filePath, String location) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        assertThat(httpRequest.getRequestUri()).isEqualTo(location);
    }

    @ParameterizedTest
    @DisplayName("Http Version 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:HTTP/1.1",
        "src/test/resources/input/get_static_file_request.txt:HTTP/1.1",
        "src/test/resources/input/get_api_request.txt:HTTP/1.1",
        "src/test/resources/input/post_api_request.txt:HTTP/1.1"
    }, delimiter = ':')
    void getHttpVersion(String filePath, String httpVersion) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        assertThat(httpRequest.getHttpVersion()).isEqualTo(httpVersion);
    }
}
