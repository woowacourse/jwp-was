package webserver.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.ServletFixture;

class HttpStartLineTest {

    @DisplayName("정상적으로 HttpStartLine 생성한다.")
    @Test
    void of() {
        HttpStartLine startLine = HttpStartLine.of(ServletFixture.REQUEST_START_LINE);

        assertThat(startLine.getMethod()).isEqualTo(MethodType.POST);
        assertThat(startLine.getProtocolVersion()).isEqualTo("HTTP/1.1");
        assertThat(startLine.getPath()).isEqualTo("/user/create");
    }
}