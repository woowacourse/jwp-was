package webserver.storage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    @Test
    void constructor() {
        Cookie cookie = new Cookie("q=qwe; a=asd; z=zxc");
        assertThat(cookie.get("q")).isEqualTo("qwe");
        assertThat(cookie.get("a")).isEqualTo("asd");
        assertThat(cookie.get("z")).isEqualTo("zxc");
    }

    @Test
    void putAndGet() {
        Cookie cookie = new Cookie();
        String key = "a";
        String value = "asd";
        cookie.put(key, value);
        assertThat(cookie.get(key)).isEqualTo(value);
    }
}