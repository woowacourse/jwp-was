package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringUtilsTest {

    @DisplayName("String형의 매개변수가 null이거나 빈 값이면 IllegalArgumentException이 발생")
    @ParameterizedTest
    @NullAndEmptySource
    void validateNonNullTest(String value) {
        assertThatThrownBy(() -> StringUtils.validateNonNullAndNotEmpty(value)).isInstanceOf(IllegalArgumentException.class);
    }
}