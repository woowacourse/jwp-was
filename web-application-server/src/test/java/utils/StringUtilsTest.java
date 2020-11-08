package utils;

import exception.InvalidHttpMessageException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class StringUtilsTest {

    @DisplayName("String형의 매개변수가 null이거나 빈 값이면 매개변수로 넘긴 Exception 발생")
    @ParameterizedTest
    @NullAndEmptySource
    void validateNonNullTest(String value) {
        Assertions.assertThatThrownBy(() -> StringUtils.validateNonNullAndNotEmpty(() -> new InvalidHttpMessageException(value),
                                                                                   value))
                .isInstanceOf(InvalidHttpMessageException.class);
    }

    @DisplayName("String형의 매개변수가 양수나 0이 아니면 true 반환")
    @ParameterizedTest
    @ValueSource(strings = {"- 54", "-1", "삼십삼", "one", "21.3", "100!", "v"})
    void isNotNumberTrueTest(String value) {
        Assertions.assertThat(StringUtils.isNotNumber(value)).isTrue();
    }

    @DisplayName("String형의 매개변수가 양수나 0이면 false 반환")
    @ParameterizedTest
    @ValueSource(strings = {"1", "9876543", "0"})
    void isNotNumberFalseTest(String value) {
        Assertions.assertThat(StringUtils.isNotNumber(value)).isFalse();
    }
}