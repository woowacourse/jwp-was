package model.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import model.general.ContentType;
import model.general.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class RequestLineTest {

    @Test
    @DisplayName("RequestLine 생성 테스트")
    void create() {
        String line = "GET /index.html HTTP/1.1";
        RequestLine requestLine = RequestLine.of(line);

        assertThat(requestLine).isInstanceOf(RequestLine.class);
    }

    @ParameterizedTest
    @DisplayName("ContentType 추출")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:.html",
        "src/test/resources/input/get_static_file_request.txt:.css"
    }, delimiter = ':')
    void extractContentType(String filePath, String expected) throws IOException {
        RequestLine requestLine = makeRequestLineFromFile(filePath);

        assertThat(requestLine.extractContentType())
            .isEqualTo(ContentType.of(expected));
    }

    @ParameterizedTest
    @DisplayName("ContentType 추출 - 확장자가 없을 경우")
    @ValueSource(strings = {
        "src/test/resources/input/get_api_request.txt",
        "src/test/resources/input/post_api_request.txt"
    })
    void extractContentType_IfNoExtension_ReturnEmpty(String filePath)
        throws IOException {
        RequestLine requestLine = makeRequestLineFromFile(filePath);

        assertThat(requestLine.extractContentType()).isEqualTo(Optional.empty());
    }

    @ParameterizedTest
    @DisplayName("Request Uri 파라미터 확인")
    @MethodSource("provideParameters")
    void extractUriParameters(String filePath, Map<String, String> parameters) throws IOException {
        RequestLine requestLine = makeRequestLineFromFile(filePath);

        assertThat(requestLine.extractUriParameters()).isEqualTo(parameters);
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
                Collections.emptyMap())
        );
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
        RequestLine requestLine = makeRequestLineFromFile(filePath);

        assertThat(requestLine.isSameMethod(Method.of(method))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("해당 경로를 Uri가 포함하고 있는지 테스트")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:/index.html",
        "src/test/resources/input/get_static_file_request.txt:/css/styles.css",
        "src/test/resources/input/get_api_request.txt:/user/create",
        "src/test/resources/input/post_api_request.txt:/user/create"
    }, delimiter = ':')
    void isSameUri(String filePath, String uri) throws IOException {
        RequestLine requestLine = makeRequestLineFromFile(filePath);

        assertThat(requestLine.isSameUri(uri)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("해당 경로를 Uri가 포함하고 있는지 테스트")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:/index.html",
        "src/test/resources/input/get_static_file_request.txt:/css/styles.css",
        "src/test/resources/input/get_api_request.txt:/user/create",
        "src/test/resources/input/post_api_request.txt:/user/create"
    }, delimiter = ':')
    void isStartsWithUri(String filePath, String uri) throws IOException {
        RequestLine requestLine = makeRequestLineFromFile(filePath);

        assertThat(requestLine.isStartsWithUri(uri)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("Get 파라미터 확인")
    @MethodSource("provideParameters")
    void extractGetParameters(String filePath, Map<String, String> parameters) throws IOException {
        RequestLine requestLine = makeRequestLineFromFile(filePath);
        Map<String, String> requestParameters = requestLine.extractUriParameters();

        assertThat(requestParameters).isEqualTo(parameters);
    }

    @ParameterizedTest
    @DisplayName("확장자 존재 여부 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:true",
        "src/test/resources/input/get_static_file_request.txt:true",
        "src/test/resources/input/get_api_request.txt:false",
        "src/test/resources/input/post_api_request.txt:false"
    }, delimiter = ':')
    void whetherUriHasExtension(String filePath, boolean expected) throws IOException {
        RequestLine requestLine = makeRequestLineFromFile(filePath);

        assertThat(requestLine.whetherUriHasExtension()).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Method 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:GET",
        "src/test/resources/input/get_static_file_request.txt:GET",
        "src/test/resources/input/get_api_request.txt:GET",
        "src/test/resources/input/post_api_request.txt:POST",
        "src/test/resources/input/post_api_request_invalid_method.txt:PUT"
    }, delimiter = ':')
    void getMethod(String filePath, String method) throws IOException {
        RequestLine requestLine = makeRequestLineFromFile(filePath);

        assertThat(requestLine.getMethod()).isEqualTo(Method.of(method));
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
        RequestLine requestLine = makeRequestLineFromFile(filePath);

        assertThat(requestLine.getRequestUri()).isEqualTo(location);
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
        RequestLine requestLine = makeRequestLineFromFile(filePath);

        assertThat(requestLine.getHttpVersion()).isEqualTo(httpVersion);
    }

    private RequestLine makeRequestLineFromFile(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String requestLine = bufferedReader.readLine();
        bufferedReader.close();

        return RequestLine.of(requestLine);
    }
}
