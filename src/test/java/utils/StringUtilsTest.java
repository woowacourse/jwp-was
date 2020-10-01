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
}