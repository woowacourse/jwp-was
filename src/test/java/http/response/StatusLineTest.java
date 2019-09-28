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
        HttpVersion version = HttpVersion.HTTP_1_1;
        StatusLine statusLine = new StatusLine(version, ResponseStatus.OK);

        assertThat(statusLine.serialize())
                .isEqualTo(String.format("%s %s", version.getVersion(), ResponseStatus.OK.serialize()));
    }

}