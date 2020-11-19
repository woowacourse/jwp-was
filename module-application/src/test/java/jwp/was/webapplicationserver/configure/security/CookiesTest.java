package jwp.was.webapplicationserver.configure.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CookiesTest {

    @DisplayName("정적 팩터리 메서드/Get - 성공")
    @Test
    void fromGet_Success() {
        String key = "abc";
        String value = "bcd";

        Cookies cookies = Cookies.from("cookie=bbb;" + key + "=" + value + ";cookies=abcd");
        assertThat(cookies.get(key)).isEqualTo(value);
    }
}
