package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestBodyTest {

    @Test
    @DisplayName("바디를 잘 파싱하는지 테스트")
    void bodyParseTest() throws IOException {
        String request =
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        RequestBody requestBody = new RequestBody(br, 93);

        assertThat(requestBody.getValue("userId")).isEqualTo("javajigi");
        assertThat(requestBody.getValue("password")).isEqualTo("password");
        assertThat(requestBody.getValue("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(requestBody.getValue("email")).isEqualTo("javajigi%40slipp.net");
    }

    @Test
    @DisplayName("[예외] 바디값의 value가 누락됐을 때 테스트")
    void invalidBodyParseTest() {
        String request =
                "userId=javajigi&password=&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        assertThatThrownBy(() -> new RequestBody(br, 85))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("password");
    }
}