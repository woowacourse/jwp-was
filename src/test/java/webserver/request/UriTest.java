package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import request.Uri;

class UriTest {

    @ParameterizedTest
    @MethodSource("getParameterOfIsUsingQueryString")
    @DisplayName("uri 가 query string 형태를 따르고 있는지 알아보기")
    void isUsingQueryString(String uri, boolean expected) {
        assertThat(new Uri(uri).isUsingQueryString()).isEqualTo(expected);
    }

    private static Stream<Arguments> getParameterOfIsUsingQueryString() {
        return Stream.of(
            Arguments.of("/join?id=abcd&password=test", true),
            Arguments.of("/users/me", false)
        );
    }

    @Test
    @DisplayName("uri 로부터 query data 얻기")
    void getDataFromGetMethodUri() {
        Uri getMethodUri = new Uri("/join?id=abcd&password=test");

        Map<String, String> dataOfGetMethodUri = new HashMap<>();
        dataOfGetMethodUri.put("id", "abcd");
        dataOfGetMethodUri.put("password", "test");

        assertThat(getMethodUri.getDataFromGetMethodUri()).isEqualTo(dataOfGetMethodUri);
    }

    @Test
    @DisplayName("uri 로부터 query data 얻기 - uri가 query string 형식을 사용하지 않을 때 예외처리")
    void getDataFromGetMethodUri_IfUriHasNotQueryData_ThrowException() {
        assertThatThrownBy(() -> new Uri("/users/me").getDataFromGetMethodUri())
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("this function can be used only when uri is query string.");
    }

    @ParameterizedTest
    @MethodSource("getParameterOfIsPathTest")
    @DisplayName("uri 의 경로 알아보기")
    void isPath(String uri, String path, boolean isPath) {
        assertThat(new Uri(uri).isPath(path)).isEqualTo(isPath);
    }

    private static Stream<Arguments> getParameterOfIsPathTest() {
        return Stream.of(
            Arguments.of("/join?id=abcd&password=test", "/join", true),
            Arguments.of("/join?id=abcd&password=test", "/joinUs", false),
            Arguments.of("/users/me", "/users/me", true),
            Arguments.of("/users/me", "/users/you", false)
        );
    }

    @Test
    @DisplayName("uri 에서 path 부분 얻기")
    void getPath() {
        Uri getMethodUri = new Uri("/join?id=abcd&password=test");
        Uri uriNotUsingQueryString = new Uri("/users/me");

        assertThat(getMethodUri.getPath()).isEqualTo("/join");
        assertThat(uriNotUsingQueryString.getPath()).isEqualTo("/users/me");
    }
}
