package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class StringUtilsTest {

    @DisplayName("빈 문자열인지 확인")
    @ParameterizedTest
    @NullAndEmptySource
    void isEmpty(String text) {
        assertThat(StringUtils.isEmpty(text)).isTrue();
    }
}
