package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RequestBodyTest {

    @Test
    void create() {
        RequestBody requestBody = RequestBody.from("userId=jjj0611&password=1234&name=장재주&email=jjj0611@gmail.com");

        assertAll(
            () -> assertThat(requestBody.get("userId")).isEqualTo("jjj0611"),
            () -> assertThat(requestBody.get("password")).isEqualTo("1234"),
            () -> assertThat(requestBody.get("name")).isEqualTo("장재주"),
            () -> assertThat(requestBody.get("email")).isEqualTo("jjj0611@gmail.com"));
    }
}