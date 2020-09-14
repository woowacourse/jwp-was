package webserver.http;

import exception.HttpMethodNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpMethodTest {

    @DisplayName("HTTP Method 문자열에 맞는 HttpMethod 객체를 반환")
    @ParameterizedTest
    @ValueSource(strings = {"GET", "POST", "PUT", "DELETE"})
    void fromTest(String methodName) {
        assertThat(HttpMethod.from(methodName)).isInstanceOf(HttpMethod.class);
    }

    @DisplayName("처리할 수 없는 HTTP Method 문자열일 경우 HttpMethodNotFoundException 발생")
    @ParameterizedTest
    @ValueSource(strings = {"Get", "poSt", "put", " delete", " "})
    void fromExceptionTest(String methodName) {
        assertThatThrownBy(() -> HttpMethod.from(methodName))
                .isInstanceOf(HttpMethodNotFoundException.class)
                .hasMessage("처리할 수 없는 HTTP Method입니다 -> " + methodName);
    }
}