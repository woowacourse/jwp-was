package utils.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeaderFieldsParserTest {
    @Test
    @DisplayName("헤더에 대한 내용을 key value 값으로 설정한다.")
    void httpHeaderFieldsParser() {
        Map<String, String> result = HttpHeaderFieldsParser.toMap("Host: localhost:8080\r\n" +
                                                                        "Connection: keep-alive\r\n" +
                                                                        "Accept: */*");

        assertThat(result.get("Host")).isEqualTo("localhost:8080");
        assertThat(result.get("Connection")).isEqualTo("keep-alive");
        assertThat(result.get("Accept")).isEqualTo("*/*");
    }

    @Test
    @DisplayName("줄바꿈이 없는 경우 헤더에 대한 내용을 key value 값으로 설정하는데 실패한다.")
    void FailHttpHeaderFieldsParser() {
        Map<String, String> result = HttpHeaderFieldsParser.toMap(
                "Host: localhost:8080 Connection: keep-alive Accept: */*");

        assertThat(result.size()).isEqualTo(0);
    }
}