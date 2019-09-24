package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {

    @DisplayName("생성한 body에 넣었던 값을 찾는 메서드인 findParam 검증")
    @Test
    void find_body() {
        Map<String, String> body = new HashMap<>();
        body.put("name", "conas");
        RequestBody requestBody = new RequestBody(body);

        assertThat(requestBody.findParam("name")).isEqualTo("conas");
    }

}