package http.request;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.IllegalRequestException;

class RequestBodyTest {

    private RequestBody requestBody;

    @DisplayName("request body가 정상적으로 parsing될때 테스트")
    @Test
    void parseRequestBodyTest() throws IOException, IllegalRequestException {
        String request = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        requestBody = new RequestBody(br, 93);

        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "javajigi");
        expected.put("password", "password");
        expected.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expected.put("email", "javajigi%40slipp.net");

        assertThat(requestBody.parseRequestBody()).isEqualTo(expected);
    }

    @DisplayName("key에 대응하는 value값이 없는 경우 예외가 발생하는지 테스트")
    @Test
    void parseRequestBodyExceptionTest() throws IOException {
        String request = "userId=";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        requestBody = new RequestBody(br, 7);

        assertThatThrownBy(() -> requestBody.parseRequestBody())
                .isInstanceOf(IllegalRequestException.class)
                .hasMessage("key, value 쌍이 완전하지 않습니다.");
    }
}
