package webserver.domain.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.common.HttpVersion;

import static org.assertj.core.api.Assertions.assertThat;

class HttpVersionTest {

    @Test
    @DisplayName("HTTP 0.9 enum 객체를 제대로 불러오는지 확인")
    void of1() {
        assertThat(HttpVersion.of("HTTP/0.9")).isEqualTo(HttpVersion.HTTP_0_9);
    }

    @Test
    @DisplayName("HTTP 1.0 enum 객체를 제대로 불러오는지 확인")
    void of2() {
        assertThat(HttpVersion.of("HTTP/1.0")).isEqualTo(HttpVersion.HTTP_1_0);
    }

    @Test
    @DisplayName("HTTP 1.1 enum 객체를 제대로 불러오는지 확인")
    void of3() {
        assertThat(HttpVersion.of("HTTP/1.1")).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @Test
    @DisplayName("존재하지 않는 HTTP 버전을 호출 시 HTTP 1.1 enum 객체를 불러오는지 확인")
    void emptyHttpVersionOf() {
        assertThat(HttpVersion.of("아무개")).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @Test
    void getVersion() {
        assertThat(HttpVersion.of("HTTP/1.1").getVersion()).isEqualTo("HTTP/1.1");
    }

}