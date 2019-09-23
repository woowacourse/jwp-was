package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    @Test
    @DisplayName("Header에서 html URL 추출")
    public void extractUrl() {
        List<String> inputs = Arrays.asList(
                "POST /user/create HTTP/1.1",
                "Content-Type: application/x-www-form-urlencoded");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(inputs);
        HttpRequestBody httpRequestBody = new HttpRequestBody(
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n");

        HttpRequest httpRequest = new HttpRequest(httpRequestHeader, httpRequestBody);
        assertThat(httpRequest.extractUrl()).isEqualTo("/user/create");
    }
}