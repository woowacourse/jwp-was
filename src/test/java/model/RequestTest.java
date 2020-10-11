package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class RequestTest {

    @ParameterizedTest
    @DisplayName("Request 생성 테스트")
    @ValueSource(strings = {
        "src/test/resources/input/get_template_file_request.txt",
        "src/test/resources/input/get_static_file_request.txt",
        "src/test/resources/input/get_api_request.txt",
        "src/test/resources/input/post_api_request.txt"
    })
    void generatePath(String filePath) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            Request request = Request.of(inputStream);

            assertThat(request).isInstanceOf(Request.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @DisplayName("메소드 확인 테스트")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:GET",
        "src/test/resources/input/get_static_file_request.txt:GET",
        "src/test/resources/input/get_api_request.txt:GET",
        "src/test/resources/input/post_api_request.txt:POST"
    }, delimiter = ':')
    void isSameMethod(String filePath, String method) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            Request request = Request.of(inputStream);

            assertThat(request.isSameMethod(Method.of(method))).isTrue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @DisplayName("경로 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:/index.html",
        "src/test/resources/input/get_static_file_request.txt:/css/styles.css",
        "src/test/resources/input/get_api_request.txt:/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",
        "src/test/resources/input/post_api_request.txt:/user/create"
    }, delimiter = ':')
    void getLocation(String filePath, String location) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            Request request = Request.of(inputStream);

            assertThat(request.getRequestUri()).isEqualTo(location);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @DisplayName("파라미터 확인")
    @MethodSource("provideParameters")
    void getParameters(String filePath, Map<String, String> parameters) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            Request request = Request.of(inputStream);
            Map<String, String> requestParameters = request.extractParameters();

            assertThat(requestParameters).isEqualTo(parameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Stream<Arguments> provideParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("password", "password");
        parameters.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        parameters.put("userId", "javajigi");
        parameters.put("email", "javajigi%40slipp.net");

        return Stream.of(
            Arguments.of("src/test/resources/input/get_template_file_request.txt", null),
            Arguments.of("src/test/resources/input/get_static_file_request.txt", null),
            Arguments.of("src/test/resources/input/get_api_request.txt", parameters),
            Arguments.of("src/test/resources/input/post_api_request.txt", parameters)
        );
    }

    @ParameterizedTest
    @DisplayName("컨텐츠 타입 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:HTML",
        "src/test/resources/input/get_static_file_request.txt:CSS",
        "src/test/resources/input/get_api_request.txt:",
        "src/test/resources/input/post_api_request.txt:"
    }, delimiter = ':')
    void getContentType(String filePath, ContentType contentType) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            Request request = Request.of(inputStream);

            assertThat(request.generateContentTypeFromRequestUri()).isEqualTo(contentType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
