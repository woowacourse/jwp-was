package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.IncorrectProtocolException;
import webserver.http.Protocol;

class ProtocolTest {
    @DisplayName("식별자에 해당하는 Protocol 객체를 생성한다.")
    @Test
    void of_shouldReturnProtocol() {
        Protocol protocol = Protocol.of("HTTP/1.1");

        assertThat(protocol).isEqualTo(Protocol.ONE_POINT_ONE);
    }

    @DisplayName("올바르지 않은 식별자의 경우 예외를 던진다.")
    @Test
    void of_shouldThrowException() {
        String source = "Incorrect Protocol";

        assertThatThrownBy(() -> Protocol.of(source))
            .isInstanceOf(IncorrectProtocolException.class)
            .hasMessageContaining(source);
    }
}