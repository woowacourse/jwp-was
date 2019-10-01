package http.response;

import http.common.HttpVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusLineTest {

    @Test
    void setHttpVersion() {
        StatusLine statusLine = new StatusLine();
        statusLine.setHttpVersion(HttpVersion.HTTP_1_1);
        assertThat(statusLine.getHttpVersion()).isEqualByComparingTo(HttpVersion.HTTP_1_1);
    }

    @Test
    void setHttpStatus() {
        StatusLine statusLine = new StatusLine();
        statusLine.setHttpStatus(HttpStatus.OK);
        assertThat(statusLine.getHttpStatus()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("statusLine이 String으로 잘 나오는지 검사")
    void getVersionAndStatusString() {
        StatusLine statusLine = new StatusLine();
        statusLine.setHttpStatus(HttpStatus.OK);
        statusLine.setHttpVersion(HttpVersion.HTTP_1_1);

        assertThat(statusLine.getVersionAndStatusString()).isEqualTo("HTTP/1.1 200 OK");
    }
}