package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DecoderUtilsTest {

    @Test
    void decodeString() {
        String email = "javajigi%40slipp.net";
        assertThat(DecoderUtils.decodeString(email)).isEqualTo("javajigi@slipp.net");
    }
}