package webserver.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.ServletFixture;

class HttpStatusLineTest {

    @DisplayName("HttpStatusLine을 추출한다.")
    @Test
    void create() {
        HttpStatusLine status = new HttpStatusLine(StatusCode.FOUND);

        assertThat(status.getProtocolVersion()).isEqualTo(ServletFixture.DEFAULT_PROTOCOL);
    }
}