package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class RequestLineTest {

    private RequestLine requestLine = new RequestLine("GET /users/me HTTP/1.1");

    @Test
    void constructor() {
        assertThat(new RequestLine("POST / HTTP/1.1")).isInstanceOf(RequestLine.class);
    }

    @Test
    void constructor_IfInputIsUnformatted_ThrowException() {
        assertThatThrownBy(() -> new RequestLine("POST /home HTTP/1.1 뀨?"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("request line input is unformatted.");

        assertThatThrownBy(() -> new RequestLine("POT /home HTTP/1.1"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("request line input is unformatted.");
    }

    @Test
    void getMethod() {
        assertThat(requestLine.getMethod()).isEqualTo("GET");
    }

    @Test
    void getUri() {
        assertThat(requestLine.getMethod()).isEqualTo("GET");
    }
}
