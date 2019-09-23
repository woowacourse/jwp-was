package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestStartLineTest {

    @Test
    @DisplayName("HttpRequestStartLine을 생성한다")
    public void startLIneTest() {
        HttpRequestStartLine httpRequestStartLine
                = new HttpRequestStartLine("GET /user/create?userId=javajigi&password=password HTTP/1.1");

        assertThat(httpRequestStartLine.getMethod()).isEqualTo("GET");
        assertThat(httpRequestStartLine.getResourcePath()).isEqualTo("/user/create");
        assertThat(httpRequestStartLine.getVersion()).isEqualTo("HTTP/1.1");
    }
}
