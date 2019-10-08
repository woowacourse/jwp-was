package webserver;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PortTest {
    @Test
    void initTest() {
        assertThat(Port.of(30).get().number()).isEqualTo(30);
        assertThat(Port.of(" 62").get().number()).isEqualTo(62);
    }

    @Test
    void invalidInputTest() {
        assertThat(Port.of("-2")).isEqualTo(Optional.empty());
        assertThat(Port.of(78887)).isEqualTo(Optional.empty());
        assertThat(Port.of(-6)).isEqualTo(Optional.empty());
        assertThat(Port.of("D.I.S.C.O")).isEqualTo(Optional.empty());
    }
}