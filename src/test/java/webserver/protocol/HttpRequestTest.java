package webserver.protocol;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

    @DisplayName("HttpRequest 생성")
    @Test
    void createTest() {
        assertDoesNotThrow(() -> HttpRequest.builder()
            .method(HttpMethod.GET)
            .path("/index.html")
            .version("HTTP/1.1")
            .queryParams(new HashMap<>())
            .headers(new HashMap<>())
            .build());
    }

    @DisplayName("[예외] HttpRequest 생성 시 null이 들어올 경우 IllegalArgumentException 발생")
    @Test
    void createTest_Fail_With_Null() {
        assertAll(
            () -> assertThatThrownBy(() -> HttpRequest.builder()
                .method(null)
                .path("/index.html")
                .version("HTTP/1.1")
                .queryParams(new HashMap<>())
                .headers(new HashMap<>())
                .build()
            )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("httpMethod가 유효하지 않습니다: null"),

            () -> assertThatThrownBy(() -> HttpRequest.builder()
            .method(HttpMethod.GET)
            .path(null)
            .version("HTTP/1.1")
            .queryParams(new HashMap<>())
            .headers(new HashMap<>())
            .build()
            )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("path가 유효하지 않습니다: null"),

            () -> assertThatThrownBy(() -> HttpRequest.builder()
            .method(HttpMethod.GET)
            .path("/index.html")
            .version(null)
            .queryParams(new HashMap<>())
            .headers(new HashMap<>())
            .build()
            )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("httpVersion가 유효하지 않습니다: null"),

            () -> assertThatThrownBy(() -> HttpRequest.builder()
            .method(HttpMethod.GET)
            .path("/index.html")
            .version("HTTP/1.1")
            .queryParams(null)
            .headers(new HashMap<>())
            .build()
            )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("queryParams가 유효하지 않습니다: null"),

            () -> assertThatThrownBy(() -> HttpRequest.builder()
            .method(HttpMethod.GET)
            .path("/index.html")
            .version("HTTP/1.1")
            .queryParams(new HashMap<>())
            .headers(null)
            .build()
            )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("headers가 유효하지 않습니다: null")
        );
    }

    @DisplayName("queryParams의 내용이 있으면 true 반환")
    @Test
    void hasQueryParamsTest_true() {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("userId", "testId");
        queryParams.put("password", "testPW");
        queryParams.put("name", "testName");
        queryParams.put("email", "test%40test.com");

        final HttpRequest httpRequest = HttpRequest.builder()
            .method(HttpMethod.GET)
            .path("/user/create")
            .version("HTTP/1.1")
            .queryParams(queryParams)
            .headers(new HashMap<>())
            .build();

        assertThat(httpRequest.hasQueryParams()).isTrue();
    }

    @DisplayName("queryParams의 내용이 없으면 false 반환")
    @Test
    void hasQueryParamsTest_false() {
        final HttpRequest httpRequest = HttpRequest.builder()
            .method(HttpMethod.GET)
            .path("/index.html")
            .version("HTTP/1.1")
            .queryParams(new HashMap<>())
            .headers(new HashMap<>())
            .build();

        assertThat(httpRequest.hasQueryParams()).isFalse();
    }

    @DisplayName("headers에 content-length가 있으면 true 반환")
    @Test
    void hasContentLengthTest_true() {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Length", "59");

        final HttpRequest httpRequest = HttpRequest.builder()
            .method(HttpMethod.POST)
            .path("/user/create")
            .version("HTTP/1.1")
            .queryParams(new HashMap<>())
            .headers(headers)
            .build();

        assertThat(httpRequest.hasContentLength()).isTrue();
    }

    @DisplayName("headers에 content-length가 없으면 false 반환")
    @Test
    void hasContentLengthTest_false() {
        final HttpRequest httpRequest = HttpRequest.builder()
            .method(HttpMethod.POST)
            .path("/user/create")
            .version("HTTP/1.1")
            .queryParams(new HashMap<>())
            .headers(new HashMap<>())
            .build();

        assertThat(httpRequest.hasContentLength()).isFalse();
    }

    @DisplayName("hearders에 accept가 text/css,*/*;q=0.1면 true 반환")
    @Test
    void isAcceptCSSTest_true() {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/css,*/*;q=0.1");

        final HttpRequest httpRequest = HttpRequest.builder()
            .method(HttpMethod.POST)
            .path("/user/create")
            .version("HTTP/1.1")
            .queryParams(new HashMap<>())
            .headers(headers)
            .build();

        assertThat(httpRequest.isAcceptCSS()).isTrue();
    }

    @DisplayName("hearders에 accept가 text/css,*/*;q=0.1가 아니면 false 반환")
    @Test
    void isAcceptCSSTest_false() {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept",
            "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n");

        final HttpRequest httpRequest = HttpRequest.builder()
            .method(HttpMethod.POST)
            .path("/user/create")
            .version("HTTP/1.1")
            .queryParams(new HashMap<>())
            .headers(headers)
            .build();

        assertThat(httpRequest.isAcceptCSS()).isFalse();
    }

    @DisplayName("hearders에 accept가 없으면 false 반환")
    @Test
    void isAcceptCSSTest_no_accept() {
        final HttpRequest httpRequest = HttpRequest.builder()
            .method(HttpMethod.POST)
            .path("/user/create")
            .version("HTTP/1.1")
            .queryParams(new HashMap<>())
            .headers(new HashMap<>())
            .build();

        assertThat(httpRequest.isAcceptCSS()).isFalse();
    }
}
