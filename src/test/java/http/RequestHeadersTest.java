package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestHeadersTest {
    @DisplayName("from: 헤더 리스트를 받아 인스턴스 생성")
    @Test
    void from() {
        // given
        List<String> headers = Arrays.asList(
                "Upgrade-Insecure-Requests: 1",
                "Sec-Fetch-Site: none",
                "Sec-Fetch-Mode: navigate");

        // when
        RequestHeaders requestHeaders = RequestHeaders.from(headers);

        // then
        assertAll(
                () -> assertThat(requestHeaders.getHeader("Upgrade-Insecure-Requests")).isEqualTo("1"),
                () -> assertThat(requestHeaders.getHeader("Sec-Fetch-Site")).isEqualTo("none"),
                () -> assertThat(requestHeaders.getHeader("Sec-Fetch-Mode")).isEqualTo("navigate")
        );
    }
}
