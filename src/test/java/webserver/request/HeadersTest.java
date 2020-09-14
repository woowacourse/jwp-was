package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.Headers;

class HeadersTest {

    @Test
    @DisplayName("요청 헤더의 특정 필드 값 읽기")
    void getValue() {
        List<String> headerFormats = new ArrayList<>();
        headerFormats.add("Authentication: bearer zxfsDeZ23u1eoi5jGwkdfv091");
        headerFormats.add("Host: localhost:8080");
        headerFormats.add("Connection: keep-alive");

        Headers headers = new Headers(headerFormats);

        assertThat(headers.getValue("Authentication"))
            .isEqualTo("bearer zxfsDeZ23u1eoi5jGwkdfv091");
        assertThat(headers.getValue("Host"))
            .isEqualTo("localhost:8080");
    }

    @Test
    @DisplayName("요청 헤더의 특정 필드 값 읽기 - 필드가 존재하지 않을 경우 예외처리")
    void getValue_IfFieldNotExist_ThrowException() {
        List<String> headerFormats = new ArrayList<>();
        headerFormats.add("Host: localhost:8080");
        headerFormats.add("Connection: keep-alive");

        Headers headers = new Headers(headerFormats);

        assertThatThrownBy(() -> headers.getValue("Authentication"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("this header field does not exist.");
    }
}
