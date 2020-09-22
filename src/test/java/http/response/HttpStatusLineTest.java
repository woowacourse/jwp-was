package http.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.HttpStatus;

public class HttpStatusLineTest {
    @Test
    @DisplayName("HttpStatusLine build 테스트")
    void build() {
        String actual  = HttpStatusLine.from("HTTP/1.1").setStatus(HttpStatus.OK).build();
        String expected = "HTTP/1.1 200 OK";
        assertThat(actual).isEqualTo(expected);
    }
}
