package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import request.Headers;

class HeadersTest {

    @Test
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
    void getValue_IfFieldNotExist_ThrowException() {
        List<String> headerFormats = new ArrayList<>();
        headerFormats.add("Authentication: bearer zxfsDeZ23u1eoi5jGwkdfv091");
        headerFormats.add("Host: localhost:8080");
        headerFormats.add("Connection: keep-alive");

        Headers headers = new Headers(headerFormats);

        assertThatThrownBy(() -> headers.getValue("cache-control"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("this header field does not exist.");
    }
}
