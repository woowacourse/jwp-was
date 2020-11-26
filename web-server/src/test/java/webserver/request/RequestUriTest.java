package webserver.request;

import exception.MissingRequestParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class RequestUriTest {

    private static Stream<Arguments> provideStringsForExtractRequestParam() {
        return Stream.of(
            Arguments
                .of("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net",
                    new HashMap<String, String>() {{
                        put("userId", "javajigi");
                        put("password", "password");
                        put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
                        put("email", "javajigi%40slipp.net");
                    }}),
            Arguments
                .of("/user/create?userId=&password=&name=&email=",
                    new HashMap<String, String>() {{
                        put("userId", "");
                        put("password", "");
                        put("name", "");
                        put("email", "");
                    }}),
            Arguments
                .of("/user/create?userId=javajigi1&userId=javajigi2&password=&name=&email=&userId=javajigi3",
                    new HashMap<String, String>() {{
                        put("userId", "javajigi1,javajigi2,javajigi3");
                        put("password", "");
                        put("name", "");
                        put("email", "");
                    }})
        );
    }

    @DisplayName("RequestUri 생성자 성공")
    @ParameterizedTest
    @ValueSource(strings = {"/user/create?userId=javajigi&password=password&name=JaeSung",
        "/index.html", "/"})
    void constructor(String input) {
        Assertions.assertThat(new RequestUri(input)).isNotNull();
    }

    @DisplayName("RequestUri 생성자 예외, Null 및 공백")
    @ParameterizedTest
    @NullAndEmptySource
    void constructor_NullAndEmpty_ThrownException(String input) {
        Assertions.assertThatThrownBy(() -> new RequestUri(input))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("RequestUri 생성자 예외, Delimiter만 있고 Parameter가 없는 경우")
    @Test
    void constructor_OnlyDelimiter_ThrownException() {
        String input = "/user/create?";
        Assertions.assertThatThrownBy(() -> new RequestUri(input)).isInstanceOf(
            MissingRequestParameterException.class);
    }

    @DisplayName("Request URI에서 Path 가져오기")
    @ParameterizedTest
    @CsvSource({"/user/create?userId=javajigi&password=password&name=JaeSung,/user/create",
        "/index.html,/index.html", "/,/index.html"})
    void getPath(String input, String expected) {
        RequestUri requestUri = new RequestUri(input);
        Assertions.assertThat(requestUri.getPath()).isEqualTo(expected);
    }

    @DisplayName("Request URI에서 Parameter 가져오기 - 성공 (정상, value가 없을 때, key가 중복될 때")
    @ParameterizedTest
    @MethodSource("provideStringsForExtractRequestParam")
    void getParameter(String input, Map<String, String> expected) {
        RequestUri requestUri = new RequestUri(input);
        List<String> params = Arrays.asList("userId", "password", "name", "email");
        params.forEach(param -> Assertions.assertThat(requestUri.getParameter(param))
            .isEqualTo(expected.get(param)));
    }

    @DisplayName("Request URI에서 Parameter 가져오기 예외, 없는 Parameters 를 가져올 경우")
    @Test
    void getParameter_NotExistParameters_ThrownException() {
        String input = "/user/create";
        Assertions.assertThatThrownBy(() -> new RequestUri(input).getParameter("userId"))
            .isInstanceOf(
                MissingRequestParameterException.class);
    }
}
