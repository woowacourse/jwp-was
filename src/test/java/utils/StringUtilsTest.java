package utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringUtilsTest {

    @Test
    @DisplayName("요청 인풋에서 path 추출 테스트")
    void getRequestLocation() {
        String requestLine = "GET /index.html HTTP/1.1";
        String requestLocation = StringUtils.getRequestLocation(requestLine);

        assertThat(requestLocation).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("요청 인풋에서 path 추출 테스트 - 올바르지 않은 인풋")
    void getRequestLocation_IfInvalidInput_ThrowException() {
        String requestLine = "";

        assertThatThrownBy(() -> StringUtils.getRequestLocation(requestLine))
            .isInstanceOf(IllegalArgumentException.class);
    }
}