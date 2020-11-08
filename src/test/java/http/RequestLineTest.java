package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {
    @DisplayName("요청 받은 requestLine 으로 method, path, parameters, protocol을 가지는 RequestLine을 생성할 수 있다.")
    @Test
    void constructRequestLine() throws IOException {
        String httpRequestInput = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
        Reader inputString = new StringReader(httpRequestInput);
        BufferedReader br = new BufferedReader(inputString);

        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("userId", "javajigi");
        expectedParameters.put("password", "password");
        expectedParameters.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expectedParameters.put("email", "javajigi%40slipp.net");

        RequestLine requestLine = new RequestLine(br);

        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/user/create");
        assertThat(requestLine.getQueryParams()).isEqualTo(expectedParameters);
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("요청 받은 requestLine에 queryString이 없으면 queryParams는 empty map이 된다.")
    @Test
    void constructRequestLineWithNoParameter() throws IOException {
        String httpRequestInput = "GET /user/create HTTP/1.1";
        Reader inputString = new StringReader(httpRequestInput);
        BufferedReader br = new BufferedReader(inputString);

        RequestLine requestLine = new RequestLine(br);

        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/user/create");
        assertThat(requestLine.getQueryParams()).isEmpty();
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP/1.1");
    }
}