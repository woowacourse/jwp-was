package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HttpBodyTest {
    @Test
    void create() {
        HttpBody httpBody = HttpBody.from("userId=jjj0611&password=1234&name=장재주&email=jjj0611@gmail.com");

        assertAll(
            () -> assertThat(httpBody.get("userId")).isEqualTo("jjj0611"),
            () -> assertThat(httpBody.get("password")).isEqualTo("1234"),
            () -> assertThat(httpBody.get("name")).isEqualTo("장재주"),
            () -> assertThat(httpBody.get("email")).isEqualTo("jjj0611@gmail.com"));
    }
}