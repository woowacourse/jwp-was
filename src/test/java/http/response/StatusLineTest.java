package http.response;

import http.response.exception.InvalidStatusLineException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatusLineTest {
    private String version = "HTTP/1.1";

    @Test
    void 생성_오류() {
        assertThrows(InvalidStatusLineException.class, () -> new StatusLine(null, ResponseStatus.OK));
        assertThrows(InvalidStatusLineException.class, () -> new StatusLine(version, null));
    }

    @Test
    void serialize_확인() {
        StatusLine statusLine = new StatusLine(version, ResponseStatus.OK);

        assertThat(statusLine.serialize()).isEqualTo(String.format("%s 200 OK", version));
    }

}