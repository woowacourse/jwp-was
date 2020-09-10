package webserver.httpmessages.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeadersTest {

    private Headers headers;

    @BeforeEach
    void setUp() {
        List<String> headerFormats = new ArrayList<>();
        headerFormats.add("Authentication: bearer zxfsDeZ23u1eoi5jGwkdfv091");
        headerFormats.add("Host: localhost:8080");
        headerFormats.add("Connection: keep-alive");

        headers = new Headers(headerFormats);
    }

    @Test
    void getValue() {
        assertThat(headers.getValue("Authentication"))
            .isEqualTo("bearer zxfsDeZ23u1eoi5jGwkdfv091");
        assertThat(headers.getValue("Host"))
            .isEqualTo("localhost:8080");
    }

    @Test
    void getValue_IfFieldNotExist_ThrowException() {
        assertThatThrownBy(() -> headers.getValue("cache-control"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("this header field does not exist.");
    }
}
