package utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class UrlUtilsTest {

    @DisplayName("Uri에서 리소스 경로 추출")
    @ParameterizedTest
    @CsvSource(value = {"/user/create?userId=javajigi&password=password, /user/create",
        "?userId=javajigi&password=password, /"})
    void extractFilePath(String input, String expected) {
        String result = UrlUtils.extractFilePath(input);
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("HttpHeader first line에서 http method 추출")
    @ParameterizedTest
    @CsvSource(value = {"GET / HTTP/1.1, GET", "POST /index.html HTTP/1.1, POST",
        "GET /user/login.html HTTP/1.1, GET"})
   void extractHttpMethod(String input, String expected) {
        String resourcePath = UrlUtils.extractHttpMethod(input);
        assertThat(resourcePath).isEqualTo(expected);
    }

    @DisplayName("HttpHeader first line에서 리소스 경로 추출")
    @ParameterizedTest
    @CsvSource(value = {"GET / HTTP/1.1, /index.html", "GET /index.html HTTP/1.1, /index.html",
        "GET /user/login.html HTTP/1.1, /user/login.html"})
    void extractResourcePath(String input, String expected) {
        String resourcePath = UrlUtils.extractResourcePath(input);
        assertThat(resourcePath).isEqualTo(expected);
    }

    @DisplayName("Request Params에서 Param 추출")
    @ParameterizedTest
    @MethodSource("provideStringsForExtractRequestParam")
    void extractRequestParam(String input, Map<String, String> expected) {
        Map<String, String> result = UrlUtils.extractRequestParam(input);
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("URL에서 Param 추출")
    @ParameterizedTest
    @MethodSource("provideStringsForExtractRequestParamFromUrl")
    void extractRequestParamFromUrl(String input, Map<String, String> expected) {
        Map<String, String> result = UrlUtils.extractRequestParamFromUrl(input);
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideStringsForExtractRequestParam() {
        return Stream.of(
            Arguments
                .of("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",
                    new HashMap<String, String>() {{
                        put("userId", "javajigi");
                        put("password", "password");
                        put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
                        put("email", "javajigi%40slipp.net");
                    }}),
            Arguments
                .of("userId=javajigi&password=password&name=%EB%B0%95%EC%=9E%AC%EC%84%B1&email=javajigi%40slipp.net&",
                    new HashMap<String, String>() {{
                        put("userId", "javajigi");
                        put("password", "password");
                        put("email", "javajigi%40slipp.net");
                    }})
        );
    }

    private static Stream<Arguments> provideStringsForExtractRequestParamFromUrl() {
        return Stream.of(
            Arguments.of(null, new HashMap<>()),
            Arguments.of("", new HashMap<>()),
            Arguments.of("  ", new HashMap<>()),
            Arguments.of("not blank", new HashMap<>()),
            Arguments
                .of("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",
                    new HashMap<String, String>() {{
                        put("userId", "javajigi");
                        put("password", "password");
                        put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
                        put("email", "javajigi%40slipp.net");
                    }}),
            Arguments
                .of("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%=9E%AC%EC%84%B1&email=javajigi%40slipp.net&",
                    new HashMap<String, String>() {{
                        put("userId", "javajigi");
                        put("password", "password");
                        put("email", "javajigi%40slipp.net");
                    }})
        );
    }
}
