package http.supoort;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DecoderTest {

    @Test
    void 디코딩_테스트() {
        String undecoded = "coogie%40gmail.com";
        assertThat(Decoder.decodeString(undecoded)).isEqualTo("coogie@gmail.com");
    }
}