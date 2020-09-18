package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

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
    //TODO 바디 파싱 실패 케이스 테스트
}