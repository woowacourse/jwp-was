package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpHeadersTest {
    @DisplayName("from: 헤더 리스트를 받아 인스턴스를 생성한다.")
    @Test
    void from() {
        // given
        List<String> headers = Arrays.asList(
                "Upgrade-Insecure-Requests: 1",
                "Sec-Fetch-Site: none",
                "Sec-Fetch-Mode: navigate");

        // when
        HttpHeaders requestHttpHeaders = HttpHeaders.from(headers);

        // then
        assertAll(
                () -> assertThat(requestHttpHeaders.getHeader("Upgrade-Insecure-Requests")).isEqualTo("1"),
                () -> assertThat(requestHttpHeaders.getHeader("Sec-Fetch-Site")).isEqualTo("none"),
                () -> assertThat(requestHttpHeaders.getHeader("Sec-Fetch-Mode")).isEqualTo("navigate")
        );
    }

    @DisplayName("empty: 빈 헤더를 생성한다.")
    @Test
    void empty() {
        // given
        HttpHeaders httpHeaders = HttpHeaders.empty();

        // when
        Map<String, String> headers = httpHeaders.getHeaders();

        // then
        assertThat(headers).isEmpty();
    }

    @DisplayName("setLocation: location을 설정한다.")
    @Test
    void setLocation() {
        // given
        HttpHeaders httpHeaders = HttpHeaders.empty();

        // when
        httpHeaders.setLocation("/");

        // then
        assertThat(httpHeaders.getHeader("Location")).isEqualTo("/");
    }

    @DisplayName("setContentType: Content-Type을 설정한다.")
    @Test
    void setContentType() {
        // given
        HttpHeaders httpHeaders = HttpHeaders.empty();

        // when
        httpHeaders.setContentType("text/html");

        // then
        assertThat(httpHeaders.getHeader("Content-Type")).isEqualTo("text/html;charset=utf-8");
    }

    @DisplayName("setContentLength: Content-Length를 설정한다.")
    @Test
    void setContentLength() {
        // given
        HttpHeaders httpHeaders = HttpHeaders.empty();

        // when
        httpHeaders.setContentLength(200);

        // then
        assertThat(httpHeaders.getContentLength()).isEqualTo(200);
    }
}
