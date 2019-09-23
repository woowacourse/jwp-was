package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHttpRequestHeaderTest {
    private HttpRequestHeader httpRequestHeader;
    private List<String> inputs;

    @BeforeEach
    public void setUp() {
        inputs = Arrays.asList(
                "GET /user/create?userId=javajigi&password=password HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        );

        httpRequestHeader = new HttpRequestHeader(inputs);
    }

    @Test
    @DisplayName("RequestHeader에서 start line에 관련된 값을 반환한다")
    public void getStartLineValue() {
        assertThat(httpRequestHeader.getMethod()).isEqualTo("GET");
        assertThat(httpRequestHeader.getResourcePath()).isEqualTo(
                "/user/create");
        assertThat(httpRequestHeader.getVersion()).isEqualTo("HTTP/1.1");

        assertThat(httpRequestHeader.get("host")).isEqualTo("localhost:8080");
        assertThat(httpRequestHeader.get("connection")).isEqualTo("keep-alive");
        assertThat(httpRequestHeader.get("accept")).isEqualTo("*/*");
    }

    @Test
    @DisplayName("Header 정보에서 key값으로 value를 가져온다")
    public void get() {
        assertThat(httpRequestHeader.get("host")).isEqualTo("localhost:8080");
        assertThat(httpRequestHeader.get("connection")).isEqualTo("keep-alive");
        assertThat(httpRequestHeader.get("accept")).isEqualTo("*/*");
    }
}
