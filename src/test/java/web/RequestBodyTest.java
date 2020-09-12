package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {
    @DisplayName("body 에서 입력 받은 데이터를 추출할 수 있다.")
    @Test
    void parseParameters() throws IOException {
        String httpRequestInput = "userId=a&password=b&name=c&email=d%40d1";
        Reader inputString = new StringReader(httpRequestInput);
        BufferedReader br = new BufferedReader(inputString);

        RequestBody requestBody = new RequestBody(br, httpRequestInput.length());

        Map<String, String> formData = requestBody.getFormData();

        assertThat(formData.keySet()).containsOnly("userId", "password", "name", "email");
        assertThat(formData.values()).containsOnly("a", "b", "c", "d%40d1");
    }

}