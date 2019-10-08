package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieTest {
    @Test
    void 쿠키_Parse_test() {
        String line = "JSESSIONID=2bbbb7bc-95fc-4982-97e4-a7090ad4eafb; logined=true";
        Cookie cookie = new Cookie();
        cookie.parse(line);

        assertThat(cookie.getCookieValue("JSESSIONID")).isEqualTo("2bbbb7bc-95fc-4982-97e4-a7090ad4eafb");
        assertThat(cookie.getCookieValue("logined")).isEqualTo("true");
    }
}
