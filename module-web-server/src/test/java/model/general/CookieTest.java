package model.general;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CookieTest {

    private static final String RAW_COOKIE = "logined=true";

    @Test
    @DisplayName("Cookie 생성")
    void create() {
        Cookie cookie = Cookie.of(RAW_COOKIE);

        assertThat(cookie).isInstanceOf(Cookie.class);
    }

    @ParameterizedTest
    @DisplayName("key가 일치하는지 확인")
    @CsvSource(value = {"logined:true", "unknown:false",}, delimiter = ':')
    void isSameKey(String key, boolean expected) {
        Cookie cookie = Cookie.of(RAW_COOKIE);

        assertThat(cookie.isSameKey(key)).isEqualTo(expected);
    }

    @Test
    @DisplayName("key 확인")
    void getKey() {
        Cookie cookie = Cookie.of(RAW_COOKIE);

        assertThat(cookie.getKey()).isEqualTo("logined");
    }

    @Test
    @DisplayName("value 확인")
    void getValue() {
        Cookie cookie = Cookie.of(RAW_COOKIE);

        assertThat(cookie.getValue()).isEqualTo("true");
    }
}