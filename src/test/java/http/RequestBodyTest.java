package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {
    @Test
    @DisplayName("바디를 잘 파싱하는지 테스트")
    void bodyParseTest() throws IOException {
        String request = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        RequestHeader requestHeader = new RequestHeader(br);
        RequestBody requestBody = new RequestBody(br, requestHeader.getContentLength());

        assertThat(requestBody.getParams().get("userId")).isEqualTo("javajigi");
        assertThat(requestBody.getParams().get("password")).isEqualTo("password");
        assertThat(requestBody.getParams().get("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(requestBody.getParams().get("email")).isEqualTo("javajigi%40slipp.net");
    }
    //TODO 바디 파싱 실패 케이스 테스트
}