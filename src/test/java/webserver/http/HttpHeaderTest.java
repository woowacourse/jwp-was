package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpHeaderTest {
    @DisplayName("입력한 헤더에 맞게 파싱하는지 테스트")
    @Test
    void ofTest() {
        String input = "Host: localhost:8080";
        HttpHeader httpHeader = HttpHeader.of(input);

        assertAll(
                () -> assertThat(httpHeader.getType()).isEqualTo("Host"),
                () -> assertThat(httpHeader.getContent()).isEqualTo("localhost:8080")
        );
    }
}
