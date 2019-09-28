package http;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static utils.StringUtils.BLANK;

class CookieTest {
    @Test
    void cookie_value_추가_테스트() {
        Cookie cookie = new Cookie();
        cookie.addAll(Arrays.asList(
                "a=1", "b=", "=4"
        ));

        assertThat(cookie.get("a")).isEqualTo("1");
        assertThat(cookie.get("b")).isEqualTo(BLANK);
        assertThat(cookie.get("c")).isNull();
    }

}