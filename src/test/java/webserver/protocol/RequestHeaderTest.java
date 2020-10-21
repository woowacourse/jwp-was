package webserver.protocol;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHeaderTest {

    @DisplayName("RequestHeader 생성")
    @Test
    void createTest() {
        final String httpMethod = "GET";
        final String path = "/index.html";
        final String httpVersion = "HTTP/1.1";

        final RequestHeader requestHeader = new RequestHeader(httpMethod, path, httpVersion);

        assertThat(new RequestHeader(httpMethod, path, httpVersion))
            .isEqualToComparingFieldByField(requestHeader);
    }

    @DisplayName("[예외] RequestHeader 생성 시 null이 들어올 경우 IllegalArgumentException 발생")
    @Test
    void createTest_Fail_With_Null() {
        final String httpMethod = "GET";
        final String path = "/index.html";
        final String httpVersion = "HTTP/1.1";

        assertAll(
            () -> assertThatThrownBy(() -> new RequestHeader(null, path, httpVersion))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("httpMethod가 유효하지 않습니다: null"),

            () -> assertThatThrownBy(() -> new RequestHeader(httpMethod, null, httpVersion))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("path가 유효하지 않습니다: null"),

            () -> assertThatThrownBy(() -> new RequestHeader(httpMethod, path, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("httpVersion가 유효하지 않습니다: null")
        );
    }
}