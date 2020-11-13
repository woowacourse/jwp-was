package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HeaderTypeTest {
    @ParameterizedTest
    @CsvSource({"Host, HOST", "Connection, CONNECTION", "Accept, ACCEPT", "Content-Type, CONTENT_TYPE"})
    void fromTest(String input, HeaderType headerType) {
        assertEquals(headerType, HeaderType.from(input));
    }

    @Test
    @DisplayName("지원하지 않는 http header 예외처리")
    void fromFailTest() {
        assertThatThrownBy(() -> HeaderType.from("Header"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("지원하지 않는 http header 입니다.");
    }
}
