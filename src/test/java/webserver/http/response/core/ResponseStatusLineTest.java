package webserver.http.response.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UtilData.REQUEST_VERSION;

class ResponseStatusLineTest {

    @Test
    @DisplayName("version 이랑 responseStatus 가 같이 들어올 때 맞는 값이 나오는지 테스트한다.")
    void version_status_Test() {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine(REQUEST_VERSION, ResponseStatus.of(200));
        assertThat(responseStatusLine.getStatusLine()).isEqualTo("HTTP/1.1 200 OK\r\n");
    }

    @Test
    @DisplayName("version 이 들어올 때 200 값이 나오는지 테스트한다.")
    void versionTest() {
        ResponseStatusLine responseStatusLine = new ResponseStatusLine(REQUEST_VERSION);
        assertThat(responseStatusLine.getStatusLine()).isEqualTo("HTTP/1.1 200 OK\r\n");
    }
}