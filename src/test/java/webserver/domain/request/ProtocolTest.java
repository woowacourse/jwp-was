package webserver.domain.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import webserver.exception.IncorrectProtocolException;

class ProtocolTest {
    @Test
    void of_shouldReturnProtocol() {
        Protocol protocol = Protocol.of("HTTP/1.1");

        assertThat(protocol).isEqualTo(Protocol.ONE_POINT_ONE);
    }

    @Test
    void of_shouldThrowException() {
        String source = "Incorrect Protocol";

        assertThatThrownBy(() -> Protocol.of(source))
            .isInstanceOf(IncorrectProtocolException.class)
            .hasMessageContaining(source);
    }
}