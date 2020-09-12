package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {
    @DisplayName("body 에서 입력 받은 데이터를 추출할 수 있다.")
    @Test
    void parseParameters() {
        String body = "userId=a&password=b&name=c&email=d%40d";

        RequestBody requestBody = new RequestBody(body);

        Map<String, String> formData = requestBody.getFormData();

        assertThat(formData.keySet()).containsOnly("userId", "password", "name", "email");
        assertThat(formData.values()).containsOnly("a", "b", "c", "d%40d");
    }

}