package webserver.protocol;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HttpMethodTest {

    @DisplayName("유효한 HttpMethod 이름일 경우 정상적으로 매핑한다.")
    @ParameterizedTest
    @ValueSource(strings = {"POST", "GET", "PUT", "DELETE"})
    void valueOfTest(final String name) {
        assertDoesNotThrow(() -> HttpMethod.valueOf(name));
    }

    @DisplayName("[예외] 유효하지 않은 HttpMethod 이름일 경우 IllegalArgumentException을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"post", "GeT", "OPTION"})
    void valueOfTest_fail(final String invalidName) {
        assertThatThrownBy(() -> HttpMethod.valueOf(invalidName))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("No enum constant");
    }
}
