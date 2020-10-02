package http.request;

import exception.IllegalRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        assertThatThrownBy(() -> requestBody.parseRequestBody()).isInstanceOf(IllegalRequestException.class)
                .hasMessage("적절하지 않은 요청입니다.");
    }
}