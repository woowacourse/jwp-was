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
        final String httpMethod = "GET";
        final String path = "/index.html";
        final String httpVersion = "HTTP/1.1";
        final Map<String, String> queryParams = new HashMap<>();

        final RequestHeader requestHeader = new RequestHeader(httpMethod, path, httpVersion, queryParams);

        assertThat(new RequestHeader(httpMethod, path, httpVersion, queryParams))
            .isEqualToComparingFieldByField(requestHeader);
    }

    @DisplayName("[예외] RequestHeader 생성 시 null이 들어올 경우 IllegalArgumentException 발생")
    @Test
    void createTest_Fail_With_Null() {
        final String httpMethod = "GET";
        final String path = "/index.html";
        final String httpVersion = "HTTP/1.1";
        final Map<String, String> queryParams = new HashMap<>();

        assertAll(
            () -> assertThatThrownBy(() -> new RequestHeader(null, path, httpVersion, queryParams))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("httpMethod가 유효하지 않습니다: null"),

            () -> assertThatThrownBy(() -> new RequestHeader(httpMethod, null, httpVersion, queryParams))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("path가 유효하지 않습니다: null"),

            () -> assertThatThrownBy(() -> new RequestHeader(httpMethod, path, null, queryParams))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("httpVersion가 유효하지 않습니다: null"),

            () -> assertThatThrownBy(() -> new RequestHeader(httpMethod, path, httpVersion, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("queryParams가 유효하지 않습니다: null")
        );
    }

    @DisplayName("queryParams의 내용이 있으면 true 반환")
    @Test
    void hasQueryParamsTest_true() {
        final String httpMethod = "GET";
        final String path = "/user/create";
        final String httpVersion = "HTTP/1.1";
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("userId", "testId");
        queryParams.put("password", "testPW");
        queryParams.put("name", "testName");
        queryParams.put("email", "test%40test.com");

        final RequestHeader requestHeader = new RequestHeader(httpMethod, path, httpVersion, queryParams);

        assertThat(requestHeader.hasQueryParams()).isTrue();
    }

    @DisplayName("queryParams의 내용이 없으면 false 반환")
    @Test
    void hasQueryParamsTest_false() {
        final String httpMethod = "GET";
        final String path = "/index.html";
        final String httpVersion = "HTTP/1.1";
        final Map<String, String> queryParams = new HashMap<>();

        final RequestHeader requestHeader = new RequestHeader(httpMethod, path, httpVersion, queryParams);

        assertThat(requestHeader.hasQueryParams()).isFalse();
    }
}
