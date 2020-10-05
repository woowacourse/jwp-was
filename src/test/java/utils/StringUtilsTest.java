package utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import model.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StringUtilsTest {

    @Test
    @DisplayName("요청 인풋에서 method 추출 테스트")
    void extractRequestMethod() {
        String requestLine = "GET /index.html HTTP/1.1";
        String requestMethod = StringUtils.extractRequestMethod(requestLine);

        assertThat(requestMethod).isEqualTo("GET");
    }

    @Test
    @DisplayName("요청 인풋에서 method 추출 테스트 - 올바르지 않은 인풋")
    void getRequestMethod_IfInvalidInput_ThrowException() {
        String requestLine = "";

        assertThatThrownBy(() -> StringUtils.extractRequestMethod(requestLine))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("요청 인풋에서 path 추출 테스트")
    void getRequestLocation() {
        String requestLine = "GET /index.html HTTP/1.1";
        String requestLocation = StringUtils.extractRequestLocation(requestLine);

        assertThat(requestLocation).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("요청 인풋에서 path 추출 테스트 - 올바르지 않은 인풋")
    void getRequestLocation_IfInvalidInput_ThrowException() {
        String requestLine = "";

        assertThatThrownBy(() -> StringUtils.extractRequestLocation(requestLine))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("요청 인풋에서 parameter 추출 테스트")
    void extractParameterValue() {
        String requestLine = "GET /user/create?userId=hamliet&password=asdasdasd&name=라흐&email=jsahn32%40gmail.com HTTP/1.1";

        assertThat(StringUtils.extractParameterValue(requestLine, "userId"))
            .isEqualTo("hamliet");
    }

    @ParameterizedTest
    @DisplayName("file의 path 추출 테스트")
    @CsvSource(value = {
        "src/test/resources/input/get_template_file_request.txt:./templates/index.html",
        "src/test/resources/input/get_static_file_request.txt:./static/css/styles.css"
    }, delimiter = ':')
    void generatePath(String filePath, String expected) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            Request request = Request.of(inputStream);

            assertThat(StringUtils.generatePath(request)).isEqualTo(expected);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("요청 인풋에서 응답 확장자 추출 테스트")
    void extractExtension() {
        String requestLine = "GET /index.html HTTP/1.1";
        String requestExtension = StringUtils.extractExtension(requestLine);

        assertThat(requestExtension).isEqualTo(".html");
    }

    @Test
    @DisplayName("요청 인풋에서 응답 확장자 추출 테스트 - 확장자가 없을 경우")
    void extractExtension_IfNoExtension_ReturnNull() {
        String requestLine = "POST /user/create HTTP/1.1";
        String requestExtension = StringUtils.extractExtension(requestLine);

        assertThat(requestExtension).isEqualTo(null);
    }

    @Test
    @DisplayName("요청 인풋에서 파라미터 추출 테스트 - GET")
    void getParametersFromGetRequest() {
        String filePath = "src/test/resources/input/get_api_request.txt";
        try {
            InputStream inputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            String method = StringUtils.extractRequestMethod(line);
            String parameters = StringUtils.getParameters(line, method, bufferedReader);

            assertThat(parameters).isEqualTo(
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("요청 인풋에서 파라미터 추출 테스트 - POST")
    void getParametersFromPostRequest() {
        String filePath = "src/test/resources/input/post_api_request.txt";
        try {
            InputStream inputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            String method = StringUtils.extractRequestMethod(line);
            String parameters = StringUtils.getParameters(line, method, bufferedReader);

            assertThat(parameters).isEqualTo(
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
