package webserver.domain.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.common.HttpVersion;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseLineTest {

    @Test
    @DisplayName("응답 라인 정보를 바이트 배열로 잘 전달하는지 확인")
    void toByte() {
        final ResponseLine line = new ResponseLine(HttpVersion.HTTP_1_1, HttpStatus.OK);
        final byte[] expect = "HTTP/1.1 200 OK".getBytes();

        assertThat(line.toBytes()).isEqualTo(expect);
    }
}