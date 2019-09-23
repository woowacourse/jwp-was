package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestStartLineTest {

    @Test
    @DisplayName("StartLine에 대한 HttpRequestLine을 생성한다")
    public void startLIneTest() {
        HttpRequestStartLine httpRequestStartLine
                = new HttpRequestStartLine("GET /user/create?userId=javajigi&password=password HTTP/1.1");

        assertThat(httpRequestStartLine.getMethod()).isEqualTo("GET");
        assertThat(httpRequestStartLine.getUrl()).isEqualTo("/user/create?userId=javajigi&password=password");
        assertThat(httpRequestStartLine.getVersion()).isEqualTo("HTTP/1.1");
    }
}
