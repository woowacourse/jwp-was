package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {
    @DisplayName("body 에서 User를 추출할 수 있다.")
    @Test
    void parseParameters() {
        String body = "userId=a&password=b&name=c&email=d%40d";

        final RequestBody requestBody = new RequestBody(body);

        final Map<String, String> user = requestBody.parseParameters();

        assertThat(user.keySet()).containsOnly("userId", "password", "name", "email");
        assertThat(user.values()).containsOnly("a", "b", "c", "d%40d");
    }

}