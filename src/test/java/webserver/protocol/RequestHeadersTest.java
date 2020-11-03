package webserver.protocol;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHeadersTest {
    @DisplayName("RequestHeaders 정상 생성")
    @Test
    void createTest() {
        assertDoesNotThrow(() -> new RequestHeaders(new HashMap<>()));
    }

    @DisplayName("RequestHeaders 생성 시 null이 들어오면 예외발생")
    @Test
    void createTest2() {
        assertThatThrownBy(() -> new RequestHeaders(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("headers가 유효하지 않습니다");
    }

    @DisplayName("ContentLength가 있으면 true를 반환")
    @Test
    void hasContentLengthTest() {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Length", "59");

        final RequestHeaders requestHeaders = new RequestHeaders(headers);
        assertThat(requestHeaders.hasContentLength()).isTrue();
    }

    @DisplayName("ContentLength가 없으면 false를 반환")
    @Test
    void hasContentLengthTest2() {
        final RequestHeaders requestHeaders = new RequestHeaders(new HashMap<>());
        assertThat(requestHeaders.hasContentLength()).isFalse();
    }

    @DisplayName("hearders에 accept가 text/css,*/*;q=0.1면 true 반환")
    @Test
    void isAcceptCSSTest_true() {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/css,*/*;q=0.1");

        final RequestHeaders requestHeaders = new RequestHeaders(headers);

        assertThat(requestHeaders.isAcceptCSS()).isTrue();
    }

    @DisplayName("hearders에 accept가 text/css,*/*;q=0.1가 아니면 false 반환")
    @Test
    void isAcceptCSSTest_false() {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept",
            "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n");

        final RequestHeaders requestHeaders = new RequestHeaders(headers);

        assertThat(requestHeaders.isAcceptCSS()).isFalse();
    }

    @DisplayName("hearders에 accept가 없으면 false 반환")
    @Test
    void isAcceptCSSTest_no_accept() {
        final RequestHeaders requestHeaders = new RequestHeaders(new HashMap<>());

        assertThat(requestHeaders.isAcceptCSS()).isFalse();
    }
}
