package http.response;

import http.common.HttpVersion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StatusLineTest {

    @Test
    void 생성() {
        StatusLine statusLine = new StatusLine();
        statusLine.setOk(HttpVersion.HTTP_1_1);

        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
    }
}
