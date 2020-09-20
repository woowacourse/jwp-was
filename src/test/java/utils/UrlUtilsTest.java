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

    @DisplayName("Request Params에서 Param 추출")
    @ParameterizedTest
    @MethodSource("provideStringsForExtractRequestParam")
    void extractRequestParam(String input, Map<String, String> expected) {
        Map<String, String> result = UrlUtils.extractRequestParam(input);
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
                .of("userId=javajigi&password=password&email=javajigi%40slipp.net&",
                    new HashMap<String, String>() {{
                        put("userId", "javajigi");
                        put("password", "password");
                        put("email", "javajigi%40slipp.net");
                    }}),
            Arguments
                .of("userId=&password=&name=&email=",
                    new HashMap<String, String>() {{
                        put("userId", "");
                        put("password", "");
                        put("name", "");
                        put("email", "");
                    }}),
            Arguments
                .of("userId=javajigi1&userId=javajigi2&password=&name=&email=&userId=javajigi3",
                    new HashMap<String, String>() {{
                        put("userId", "javajigi1,javajigi2,javajigi3");
                        put("password", "");
                        put("name", "");
                        put("email", "");
                    }})
        );
    }
}
