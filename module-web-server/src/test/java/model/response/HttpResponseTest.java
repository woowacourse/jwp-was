package model.response;

import static org.assertj.core.api.Assertions.assertThat;

import controller.Controller;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;
import model.general.Header;
import model.general.Headers;
import model.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import utils.ControllerMapper;
import utils.FileIoUtils;

public class HttpResponseTest {

    @ParameterizedTest
    @DisplayName("Response 생성")
    @ValueSource(strings = {
        "src/test/resources/input/get_template_file_request.txt",
        "src/test/resources/input/get_static_file_request.txt",
        "src/test/resources/input/get_api_request.txt",
        "src/test/resources/input/post_api_request.txt"
    })
    void create(String filePath) throws IOException {
        HttpResponse httpResponse = makeHttpResponseFromFile(filePath);

        assertThat(httpResponse).isInstanceOf(HttpResponse.class);
    }

    @ParameterizedTest
    @DisplayName("Output 작성 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:"
            + "src/test/resources/output/get_template_file_request_output.txt:"
            + "HTTP/1.1 200 OK",
        "src/test/resources/input/get_static_file_request.txt:"
            + "src/test/resources/output/get_static_file_request_output.txt:"
            + "HTTP/1.1 200 OK"
    }, delimiter = ':')
    void writeToOutputStream(String inputFilePath, String outputFilePath, String statusLine)
        throws IOException {
        InputStream inputStream = new FileInputStream(inputFilePath);
        OutputStream outputStream = new FileOutputStream(outputFilePath);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        HttpRequest httpRequest = HttpRequest.of(inputStream);

        ControllerMapper controllerMapper = new ControllerMapper(Collections.emptyMap());
        Controller controller = controllerMapper.selectController(httpRequest);
        HttpResponse httpResponse = controller.service(httpRequest);

        httpResponse.writeToOutputStream(dataOutputStream);

        inputStream = new FileInputStream(outputFilePath);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        assertThat(bufferedReader.readLine()).contains(statusLine);
    }

    @ParameterizedTest
    @DisplayName("HttpVersion 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:HTTP/1.1",
        "src/test/resources/input/get_static_file_request.txt:HTTP/1.1"
    }, delimiter = ':')
    void getHttpVersion(String filePath, String expected) throws IOException {
        HttpResponse httpResponse = makeHttpResponseFromFile(filePath);

        assertThat(httpResponse.getHttpVersion()).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("StatusCode 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:200",
        "src/test/resources/input/get_static_file_request.txt:200"
    }, delimiter = ':')
    void getStatusCode(String filePath, String expected) throws IOException {
        HttpResponse httpResponse = makeHttpResponseFromFile(filePath);

        assertThat(httpResponse.getStatusCode()).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("reasonPhrase 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:OK",
        "src/test/resources/input/get_static_file_request.txt:OK"
    }, delimiter = ':')
    void getReasonPhrase(String filePath, String expected) throws IOException {
        HttpResponse httpResponse = makeHttpResponseFromFile(filePath);

        assertThat(httpResponse.getReasonPhrase()).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("headers 확인")
    @MethodSource("provideHeaders")
    void getHeaders(String filePath, Headers expected) throws IOException {
        HttpResponse httpResponse = makeHttpResponseFromFile(filePath);

        assertThat(httpResponse.getHeaders()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideHeaders() throws IOException, URISyntaxException {
        Headers getMethodTemplateHeaders = new Headers();
        byte[] indexHtmlBody = FileIoUtils
            .loadFileFromClasspath("templates/index.html");
        getMethodTemplateHeaders
            .addHeader(Header.CONTENT_LENGTH, String.valueOf(indexHtmlBody.length));
        getMethodTemplateHeaders.addHeader(Header.CONTENT_TYPE, "text/html");

        Headers getMethodStaticHeaders = new Headers();
        byte[] styleCssBody = FileIoUtils
            .loadFileFromClasspath("static/css/styles.css");
        getMethodStaticHeaders
            .addHeader(Header.CONTENT_LENGTH, String.valueOf(styleCssBody.length));
        getMethodStaticHeaders.addHeader(Header.CONTENT_TYPE, "text/css");

        return Stream.of(
            Arguments.of("src/test/resources/input/get_template_file_request.txt",
                getMethodTemplateHeaders),
            Arguments.of("src/test/resources/input/get_static_file_request.txt",
                getMethodStaticHeaders)
        );
    }

    @ParameterizedTest
    @DisplayName("body 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:true",
        "src/test/resources/input/get_static_file_request.txt:true",
        "src/test/resources/input/get_api_request.txt:false",
        "src/test/resources/input/post_api_request.txt:false",
        "src/test/resources/input/post_api_request_invalid_method.txt:false"
    }, delimiter = ':')
    void getBody(String filePath, boolean expected) throws IOException {
        HttpResponse httpResponse = makeHttpResponseFromFile(filePath);
        byte[] body = httpResponse.getBody();

        assertThat(Objects.nonNull(body)).isEqualTo(expected);
    }

    private HttpResponse makeHttpResponseFromFile(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        ControllerMapper controllerMapper = new ControllerMapper(Collections.emptyMap());
        Controller controller = controllerMapper.selectController(httpRequest);

        return controller.service(httpRequest);
    }
}
