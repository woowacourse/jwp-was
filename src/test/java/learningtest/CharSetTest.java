package learningtest;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class CharSetTest {

    // https://stackoverflow.com/questions/42912994/null-characters-added-when-a-string-is-encoded-into-utf-8-bytes
    @Test
    void UTF_8_String() {
        String tobeEncoded = "안녕하세요";

        byte[] b = tobeEncoded.getBytes(StandardCharsets.UTF_8);

        // \xEC\x95\x88\xEB\x85\x95\xED\x95\x98\xEC\x84\xB8\xEC\x9A\x94
        assertThat(b.length).isEqualTo(15);
        assertThat(new String(b, StandardCharsets.UTF_8)).isEqualTo(tobeEncoded);
    }
}
