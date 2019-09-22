package webserver.http.headerfields;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpPortTest {
    private static final int MAX_PORT_NUMBER = 65535;
    private static final int MIN_PORT_NUMBER = 0;


    @Test
    @DisplayName("정상적인 HttpPort 객체를 생성한다.")
    void createHttpPort() {
        HttpPort httpPort = HttpPort.of(80)
                .orElseThrow(IllegalArgumentException::new);

        assertThat(httpPort.number()).isEqualTo(80);
    }

    @Test
    @DisplayName("기본값 이외에 정상적인 HttpPort 객체를 생성한다.")
    void createHttpPort2() {
        HttpPort httpPort = HttpPort.of(7070)
                .orElseThrow(IllegalArgumentException::new);

        assertThat(httpPort.number()).isEqualTo(7070);
    }

    @Test
    @DisplayName("정상 범위가 아닌 HttpPort 객체를 생성한다.")
    void createFailHttpPort() {
        assertThrows(IllegalArgumentException.class, () ->
                HttpPort.of(MIN_PORT_NUMBER - 1).orElseThrow(IllegalArgumentException::new)
        );
    }

    @Test
    @DisplayName("정상 범위가 아닌 HttpPort 객체를 생성한다.")
    void createFailHttpPort2() {
        assertThrows(IllegalArgumentException.class, () ->
                HttpPort.of(MAX_PORT_NUMBER + 1).orElseThrow(IllegalArgumentException::new)
        );
    }

}