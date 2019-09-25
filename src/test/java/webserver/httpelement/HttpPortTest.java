package webserver.httpelement;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HttpPortTest {
    @Test
    void initTest() {
        assertThat(HttpPort.of(30).get().number()).isEqualTo(30);
        assertThat(HttpPort.of(" 62").get().number()).isEqualTo(62);
    }

    @Test
    void invalidInputTest() {
        assertThat(HttpPort.of("-2")).isEqualTo(Optional.empty());
        assertThat(HttpPort.of(78887)).isEqualTo(Optional.empty());
        assertThat(HttpPort.of(-6)).isEqualTo(Optional.empty());
        assertThat(HttpPort.of("D.I.S.C.O")).isEqualTo(Optional.empty());
    }
}