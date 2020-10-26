package http;

import http.request.RequestBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestBodyTest {

    @Test
    @DisplayName("바디를 잘 파싱하는지 테스트")
    void bodyParseTest() throws IOException {
        String request =
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        RequestBody requestBody = new RequestBody(br, 93, "application/x-www-form-urlencoded");

        assertEquals("javajigi", requestBody.getValue("userid"));
        assertEquals("password", requestBody.getValue("password"));
        assertEquals("%EB%B0%95%EC%9E%AC%EC%84%B1", requestBody.getValue("name"));
        assertEquals("javajigi%40slipp.net", requestBody.getValue("email"));
    }

    @Test
    @DisplayName("[예외] 바디값의 value가 누락됐을 때 테스트")
    void invalidBodyParseTest() {
        String request =
                "userId=javajigi&password=&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        assertThatThrownBy(() -> new RequestBody(br, 85, "application/x-www-form-urlencoded"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("password");
    }

    @Test
    @DisplayName("[예외] content-type이 미지원되는 타입일 경우 테스트")
    void unsupportedContentTypeTest() {
        String request = "{" +
                "userId : javajigi," +
                "password: password," +
                "name: %EB%B0%95%EC%9E%AC%EC%84%B1," +
                "email: javajigi%40slipp.net " +
                "}";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        assertThatThrownBy(() -> new RequestBody(br, 85, "application/json"))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("content-type");
    }
}