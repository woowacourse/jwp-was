package request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MethodTest {

    @ParameterizedTest
    @MethodSource("getParameterOfFromTest")
    @DisplayName("문자열로부터 enum 객체 찾기")
    void from(String methodString, Method method) {
        assertThat(Method.from(methodString)).isEqualTo(method);
    }

    private static Stream<Arguments> getParameterOfFromTest() {
        return Stream.of(
            Arguments.of("GET", Method.GET),
            Arguments.of("POST", Method.POST),
            Arguments.of("PUT", Method.PUT),
            Arguments.of("DELETE", Method.DELETE)
        );
    }

    @Test
    @DisplayName("문자열로부터 Method 객체 찾기 - 문자열에 대응하는 Method가 없는 경우 예외처리")
    void from_IfNotExistMethod_ThrowException() {
        assertThatThrownBy(() -> Method.from("gget"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("this HTTP method does not exist.");
    }

    @ParameterizedTest
    @MethodSource("getParameterOfGetMethodTest")
    @DisplayName("Method 객체의 문자열 표현 얻기")
    void getMethod(Method method, String methodString) {
        assertThat(method.getMethod()).isEqualTo(methodString);
    }

    private static Stream<Arguments> getParameterOfGetMethodTest() {
        return Stream.of(
            Arguments.of(Method.GET, "GET"),
            Arguments.of(Method.POST, "POST"),
            Arguments.of(Method.PUT, "PUT"),
            Arguments.of(Method.DELETE, "DELETE")
        );
    }
}
