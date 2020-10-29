package webserver.protocol;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHeaderTest {

    @DisplayName("RequestHeader 생성")
    @Test
    void createTest() {
        final HttpMethod httpMethod = HttpMethod.GET;
        final String path = "/index.html";
        final String httpVersion = "HTTP/1.1";
        final Map<String, String> queryParams = new HashMap<>();
        final Map<String, String> headers = new HashMap<>();

        final RequestHeader requestHeader = new RequestHeader(httpMethod, path, httpVersion, queryParams, headers);

        assertThat(new RequestHeader(httpMethod, path, httpVersion, queryParams, headers))
            .isEqualToComparingFieldByField(requestHeader);
    }

    @DisplayName("[예외] RequestHeader 생성 시 null이 들어올 경우 IllegalArgumentException 발생")
    @Test
    void createTest_Fail_With_Null() {
        final HttpMethod httpMethod = HttpMethod.GET;
        final String path = "/index.html";
        final String httpVersion = "HTTP/1.1";
        final Map<String, String> queryParams = new HashMap<>();
        final Map<String, String> headers = new HashMap<>();

        assertAll(
            () -> assertThatThrownBy(() -> new RequestHeader(null, path, httpVersion, queryParams, headers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("httpMethod가 유효하지 않습니다: null"),

            () -> assertThatThrownBy(() -> new RequestHeader(httpMethod, null, httpVersion, queryParams, headers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("path가 유효하지 않습니다: null"),

            () -> assertThatThrownBy(() -> new RequestHeader(httpMethod, path, null, queryParams, headers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("httpVersion가 유효하지 않습니다: null"),

            () -> assertThatThrownBy(() -> new RequestHeader(httpMethod, path, httpVersion, null, headers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("queryParams가 유효하지 않습니다: null"),

            () -> assertThatThrownBy(() -> new RequestHeader(httpMethod, path, httpVersion, queryParams, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("headers가 유효하지 않습니다: null")
        );
    }

    @DisplayName("queryParams의 내용이 있으면 true 반환")
    @Test
    void hasQueryParamsTest_true() {
        final HttpMethod httpMethod = HttpMethod.GET;
        final String path = "/user/create";
        final String httpVersion = "HTTP/1.1";
        final Map<String, String> queryParams = new HashMap<>();
        final Map<String, String> headers = new HashMap<>();
        queryParams.put("userId", "testId");
        queryParams.put("password", "testPW");
        queryParams.put("name", "testName");
        queryParams.put("email", "test%40test.com");

        final RequestHeader requestHeader = new RequestHeader(httpMethod, path, httpVersion, queryParams, headers);

        assertThat(requestHeader.hasQueryParams()).isTrue();
    }

    @DisplayName("queryParams의 내용이 없으면 false 반환")
    @Test
    void hasQueryParamsTest_false() {
        final HttpMethod httpMethod = HttpMethod.GET;
        final String path = "/index.html";
        final String httpVersion = "HTTP/1.1";
        final Map<String, String> queryParams = new HashMap<>();
        final Map<String, String> headers = new HashMap<>();

        final RequestHeader requestHeader = new RequestHeader(httpMethod, path, httpVersion, queryParams, headers);

        assertThat(requestHeader.hasQueryParams()).isFalse();
    }

    @DisplayName("headers에 content-length가 있으면 true 반환")
    @Test
    void hasContentLengthTest_true() {
        final HttpMethod httpMethod = HttpMethod.POST;
        final String path = "/user/create";
        final String httpVersion = "HTTP/1.1";
        final Map<String, String> queryParams = new HashMap<>();
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Length", "59");

        final RequestHeader requestHeader = new RequestHeader(httpMethod, path, httpVersion, queryParams, headers);

        assertThat(requestHeader.hasContentLength()).isTrue();
    }

    @DisplayName("headers에 content-length가 없으면 false 반환")
    @Test
    void hasContentLengthTest_false() {
        final HttpMethod httpMethod = HttpMethod.POST;
        final String path = "/user/create";
        final String httpVersion = "HTTP/1.1";
        final Map<String, String> queryParams = new HashMap<>();
        final Map<String, String> headers = new HashMap<>();

        final RequestHeader requestHeader = new RequestHeader(httpMethod, path, httpVersion, queryParams, headers);

        assertThat(requestHeader.hasContentLength()).isFalse();
    }
}
