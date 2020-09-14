package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestPathUtilTest {
    @DisplayName("Rqeust line에서 path를 분리한다.")
    @Test
    void getPathFromRequestLineTest() {
        String requestLine = "GET /index.html HTTP/1.1";
        String path = RequestPathUtil.getPathFromRequestLine(requestLine);

        assertThat(path).isEqualTo("/index.html");
    }
}
