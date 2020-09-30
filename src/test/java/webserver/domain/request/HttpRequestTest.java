package webserver.domain.request;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {
    @DisplayName("GET 요청에 대한 HttpRequest 객체를 생성한다. ")
    @Test
    void of_whenRequestMethodIsGet() throws IOException {
        InputStream inputStream = new FileInputStream(
            new File("/Users/moon/Desktop/Github/jwp-was/build/resources/test/GetRequest.txt"));

        HttpRequest httpRequest = HttpRequest.of(inputStream);

        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("userId", "javajigi");
        expectedParameters.put("password", "password");
        expectedParameters.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expectedParameters.put("email", "javajigi%40slipp.net");
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getParameters()).isEqualTo(expectedParameters);
    }

    @DisplayName("요청에 해당하는 templates 자원의 경로를 반환한다.")
    @Test
    void getPath_whenRequestTemplatesFiles() throws IOException {
        InputStream inputStream = new FileInputStream(
            new File("/Users/moon/Desktop/Github/jwp-was/build/resources/test/TemplatesResourceRequest.txt"));
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        String path = httpRequest.getPath();

        assertThat(path).isEqualTo("./templates/index.html");
    }

    @DisplayName("요청에 해당하는 static 자원의 경로를 반환한다.")
    @Test
    void getPath_whenRequestStaticFiles() throws IOException {
        InputStream inputStream = new FileInputStream(
            new File("/Users/moon/Desktop/Github/jwp-was/build/resources/test/StaticResourceRequest.txt"));
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        String path = httpRequest.getPath();

        assertThat(path).isEqualTo("./static/css/styles.css");
    }

    @DisplayName("요청에 해당하는 동적 처리 경로를 반환한다.")
    @Test
    void getPath_whenRequestNotResources() throws IOException {
        InputStream inputStream = new FileInputStream(
            new File("/Users/moon/Desktop/Github/jwp-was/build/resources/test/GetRequest.txt"));
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        String path = httpRequest.getPath();

        assertThat(path).isEqualTo("/user/create");
    }
}