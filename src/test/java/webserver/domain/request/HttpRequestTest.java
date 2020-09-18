package webserver.domain.request;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.domain.Header;

class HttpRequestTest {
    @DisplayName("요청에 해당하는 templates 자원의 경로를 반환한다.")
    @Test
    void getPath_whenRequestTemplatesFiles() {
        RequestLine requestLine = RequestLine.of("GET /index.html HTTP/1.1");
        Map<String, String> headerFields = new HashMap<>();
        headerFields.put("Host", "localhost:8080");
        headerFields.put("Connection", "keep-alive");
        headerFields.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        Header header = new Header(headerFields);
        HttpRequest httpRequest = new HttpRequest(requestLine, header);

        String path = httpRequest.getPath();

        assertThat(path).isEqualTo("./templates/index.html");
    }

    @DisplayName("요청에 해당하는 static 자원의 경로를 반환한다.")
    @Test
    void getPath_whenRequestStaticFiles() {
        RequestLine requestLine = RequestLine.of("GET /css/styles.css HTTP/1.1");
        Map<String, String> headerFields = new HashMap<>();
        headerFields.put("Host", "localhost:8080");
        headerFields.put("Connection", "keep-alive");
        headerFields.put("Accept", "text/css,*/*;q=0.1");
        Header header = new Header(headerFields);
        HttpRequest httpRequest = new HttpRequest(requestLine, header);

        String path = httpRequest.getPath();

        assertThat(path).isEqualTo("./static/css/styles.css");
    }

    @DisplayName("요청에 해당하는 동적 처리 경로를 반환한다.")
    @Test
    void getPath_whenRequestNotResources() {
        RequestLine requestLine = RequestLine.of(
            "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");
        Map<String, String> headerFields = new HashMap<>();
        headerFields.put("Host", "localhost:8080");
        headerFields.put("Connection", "keep-alive");
        headerFields.put("Accept", "*/*");
        Header header = new Header(headerFields);
        HttpRequest httpRequest = new HttpRequest(requestLine, header);

        String path = httpRequest.getPath();

        assertThat(path).isEqualTo("/user/create");
    }

    @DisplayName("GET 요청에 대한 HttpRequest 객체를 생성한다. ")
    @Test
    void of_whenRequestMethodIsGet() throws IOException {
        File file = new File("/Users/moon/Desktop/Github/jwp-was/build/resources/test/GetRequest.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        HttpRequest httpRequest = HttpRequest.of(br);

        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("userId", "javajigi");
        expectedParameters.put("password", "password");
        expectedParameters.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expectedParameters.put("email", "javajigi%40slipp.net");
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getParameters()).isEqualTo(expectedParameters);
    }

}