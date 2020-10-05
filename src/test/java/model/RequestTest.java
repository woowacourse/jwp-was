package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @Test
    @DisplayName("Request 생성 테스트 - .지원하지 않는 메소드")
    void generatePath() {
        String filePath = "src/test/resources/input/put_api_request.txt";
        try {
            InputStream inputStream = new FileInputStream(filePath);

            assertThatThrownBy(() -> Request.of(inputStream))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not Implemented Method");
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
        "src/test/resources/input/get_static_file_request.txt:/css/style.css",
        "src/test/resources/input/get_api_request.txt:/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",
        "src/test/resources/input/post_api_request.txt:/user/create"
    }, delimiter = ':')
    void getLocation(String filePath, String location) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            Request request = Request.of(inputStream);

            assertThat(request.getLocation()).isEqualTo(location);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @DisplayName("파라미터 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:",
        "src/test/resources/input/get_static_file_request.txt:",
        "src/test/resources/input/get_api_request.txt:userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",
        "src/test/resources/input/post_api_request.txt:userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
    }, delimiter = ':')
    void getParameters(String filePath, String parameters) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            Request request = Request.of(inputStream);

            assertThat(request.getParameters()).isEqualTo(parameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

            assertThat(request.getContentType()).isEqualTo(contentType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
