package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {
    private String blankText = "";
    private String nullText = null;

    @Test
    void isNotEmptyTest() {
        // when & then
        assertThat(StringUtils.isNotEmpty(blankText)).isFalse();
        assertThat(StringUtils.isNotEmpty(nullText)).isFalse();
    }

    @Test
    void isEmptyTest() {
        // when & then
        assertThat(StringUtils.isEmpty(blankText)).isTrue();
        assertThat(StringUtils.isEmpty(nullText)).isTrue();
    }
}