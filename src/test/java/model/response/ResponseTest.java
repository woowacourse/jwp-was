package model.response;

import static org.assertj.core.api.Assertions.assertThat;

import controller.Controller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import model.general.Header;
import model.request.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ResponseTest {

    @ParameterizedTest
    @DisplayName("Response 생성")
    @ValueSource(strings = {
        "src/test/resources/input/get_template_file_request.txt",
        "src/test/resources/input/get_static_file_request.txt",
        "src/test/resources/input/get_api_request.txt",
        "src/test/resources/input/post_api_request.txt"
    })
    void create(String filePath) throws IOException, URISyntaxException {
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);
        Response response = Controller.executeOperation(request);

        assertThat(response).isInstanceOf(Response.class);
    }

    @ParameterizedTest
    @DisplayName("Response Body 존재 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:true",
        "src/test/resources/input/get_static_file_request.txt:true",
        "src/test/resources/input/get_api_request.txt:false",
        "src/test/resources/input/post_api_request.txt:false"
    }, delimiter = ':')
    void hasContents(String filePath, boolean expected) throws IOException, URISyntaxException {
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);
        Response response = Controller.executeOperation(request);

        assertThat(response.hasContents()).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("HttpVersion 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:HTTP/1.1",
        "src/test/resources/input/get_static_file_request.txt:HTTP/1.1",
        "src/test/resources/input/get_api_request.txt:HTTP/1.1",
        "src/test/resources/input/post_api_request.txt:HTTP/1.1"
    }, delimiter = ':')
    void getHttpVersion(String filePath, String expected) throws IOException, URISyntaxException {
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);
        Response response = Controller.executeOperation(request);

        assertThat(response.getHttpVersion()).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("StatusCode 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:200",
        "src/test/resources/input/get_static_file_request.txt:200",
        "src/test/resources/input/get_api_request.txt:405",
        "src/test/resources/input/post_api_request.txt:302",
        "src/test/resources/input/put_api_request.txt:405"
    }, delimiter = ':')
    void getStatusCode(String filePath, String expected) throws IOException, URISyntaxException {
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);
        Response response = Controller.executeOperation(request);

        assertThat(response.getStatusCode()).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("reasonPhrase 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:OK",
        "src/test/resources/input/get_static_file_request.txt:OK",
        "src/test/resources/input/get_api_request.txt:Method Not Allowed",
        "src/test/resources/input/post_api_request.txt:Found",
        "src/test/resources/input/put_api_request.txt:Method Not Allowed"
    }, delimiter = ':')
    void getReasonPhrase(String filePath, String expected) throws IOException, URISyntaxException {
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);
        Response response = Controller.executeOperation(request);

        assertThat(response.getReasonPhrase()).isEqualTo(expected);
    }

    //todo: 테스트 깨지는지 확인
    @ParameterizedTest
    @DisplayName("headers 확인")
    @MethodSource("provideHeaders")
    void getHeaders(String filePath, Map<Header, String> expected)
        throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);
        Response response = Controller.executeOperation(request);

        assertThat(response.getHeaders()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideHeaders() {
        Map<Header, String> getMethodTemplateHeaders = new HashMap<>();
        getMethodTemplateHeaders.put(Header.CONTENT_LENGTH, "7049");
        getMethodTemplateHeaders.put(Header.CONTENT_TYPE, "text/html");

        Map<Header, String> getMethodStaticHeaders = new HashMap<>();
        getMethodStaticHeaders.put(Header.CONTENT_LENGTH, "7268");
        getMethodStaticHeaders.put(Header.CONTENT_TYPE, "text/css");

        Map<Header, String> getMethodApiHeaders = new HashMap<>();

        Map<Header, String> postMethodApiHeaders = new HashMap<>();
        postMethodApiHeaders.put(Header.LOCATION, "/index.html");

        return Stream.of(
            Arguments.of("src/test/resources/input/get_template_file_request.txt",
                getMethodTemplateHeaders),
            Arguments.of("src/test/resources/input/get_static_file_request.txt",
                getMethodStaticHeaders),
            Arguments.of("src/test/resources/input/get_api_request.txt",
                getMethodApiHeaders),
            Arguments.of("src/test/resources/input/post_api_request.txt",
                postMethodApiHeaders)
        );
    }

    @ParameterizedTest
    @DisplayName("body 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:true",
        "src/test/resources/input/get_static_file_request.txt:true",
        "src/test/resources/input/get_api_request.txt:false",
        "src/test/resources/input/post_api_request.txt:false",
        "src/test/resources/input/put_api_request.txt:false"
    }, delimiter = ':')
    void getBody(String filePath, boolean expected) throws IOException, URISyntaxException {
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);
        Response response = Controller.executeOperation(request);

        byte[] body = response.getBody();

        assertThat(Objects.nonNull(body)).isEqualTo(expected);
    }
}
