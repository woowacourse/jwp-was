package webserver.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class StatusLineTest {

    @DisplayName("StatusLine 생성자 성공")
    @Test
    void constructor() {
        String input = "HTTP/1.1 200";
        Assertions.assertThat(new StatusLine(input)).isNotNull();
    }

    @DisplayName("StatusLine 생성자 예외, Null 및 공백")
    @ParameterizedTest
    @NullAndEmptySource
    void constructor_NullAndEmpty_ThrownException(String input) {
        Assertions.assertThatThrownBy(() -> new StatusLine(input))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
