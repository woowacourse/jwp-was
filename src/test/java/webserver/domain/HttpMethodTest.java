package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpMethodTest {

    @Test
    @DisplayName("GET enum 객체를 불러오는지 확인")
    void create1() {
        assertThat(HttpMethod.valueOf("GET")).isEqualTo(HttpMethod.GET);
    }

    @Test
    @DisplayName("POST enum 객체를 불러오는지 확인")
    void create2() {
        assertThat(HttpMethod.valueOf("POST")).isEqualTo(HttpMethod.POST);
    }

    @Test
    @DisplayName("PUT enum 객체를 불러오는지 확인")
    void create3() {
        assertThat(HttpMethod.valueOf("PUT")).isEqualTo(HttpMethod.PUT);
    }

    @Test
    @DisplayName("DELETE enum 객체를 불러오는지 확인")
    void create4() {
        assertThat(HttpMethod.valueOf("DELETE")).isEqualTo(HttpMethod.DELETE);
    }
}