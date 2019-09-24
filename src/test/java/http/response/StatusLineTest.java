package http.response;

import http.common.HttpVersion;
import http.response.exception.InvalidStatusLineException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusLineTest {

    @Test
    void 생성_오류() {
        assertThrows(InvalidStatusLineException.class, () -> new StatusLine(null, ResponseStatus.OK));
        assertThrows(InvalidStatusLineException.class, () -> new StatusLine(HttpVersion.HTTP_1_1, null));
    }

    @Test
    void serialize_확인() {
        String version = "HTTP/1.1";
        StatusLine statusLine = new StatusLine(HttpVersion.HTTP_1_1, ResponseStatus.OK);

        assertThat(statusLine.serialize()).isEqualTo(String.format("%s 200 OK", version));
    }

}