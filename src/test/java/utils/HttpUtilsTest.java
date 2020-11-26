package utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class HttpUtilsTest {
    @Test
    void urlDecode() {
        String actual = "%EC%95%84%EC%9D%B4%EC%9C%A0";
        String expected = "아이유";

        assertThat(HttpUtils.urlDecode(actual)).isEqualTo(expected);
    }
}
