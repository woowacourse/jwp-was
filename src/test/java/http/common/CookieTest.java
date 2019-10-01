package http.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CookieTest {

    private Cookie cookie;

    @BeforeEach
    void setUp() {
        cookie = new Cookie("name","value");
    }

    @Test
    void getName() {
        assertThat(cookie.getName()).isEqualTo("name");
    }

    @Test
    void getValue() {
        assertThat(cookie.getValue()).isEqualTo("value");
    }
}