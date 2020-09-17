package webserver.domain.request;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class HttpRequestTest {
    @DisplayName("요청에 해당하는 templates 자원의 경로를 반환한다.")
    @Test
    void getPath_whenRequestTemplatesFiles() {
        RequestLine requestLine = RequestLine.of("GET /index.html HTTP/1.1");
        String header = "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n"
            + "Sec-Fetch-Site: none\n"
            + "Sec-Fetch-Mode: navigate\n"
            + "Sec-Fetch-User: ?1\n"
            + "Sec-Fetch-Dest: document\n"
            + "Accept-Encoding: gzip, deflate, br\n"
            + "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6,vi;q=0.5,la;q=0.4\n"
            + "Cookie: Idea-3be1aa82=33bac591-b163-42cb-9b63-333572a05b11";
        HttpRequest httpRequest = new HttpRequest(requestLine, header, "");

        assertThat(httpRequest.getPath()).isEqualTo("./templates/index.html");
    }

    @DisplayName("요청에 해당하는 static 자원의 경로를 반환한다.")
    @Test
    void getPath_whenRequestStaticFiles() {
        RequestLine requestLine = RequestLine.of("GET /css/styles.css HTTP/1.1");
        String header = "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36\n"
            + "Accept: text/css,*/*;q=0.1\n"
            + "Sec-Fetch-Site: same-origin\n"
            + "Sec-Fetch-Mode: no-cors\n"
            + "Sec-Fetch-Dest: style\n"
            + "Referer: http://localhost:8080/index.html\n"
            + "Accept-Encoding: gzip, deflate, br\n"
            + "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6,vi;q=0.5,la;q=0.4\n"
            + "Cookie: Idea-3be1aa82=33bac591-b163-42cb-9b63-333572a05b11\n";
        HttpRequest httpRequest = new HttpRequest(requestLine, header, "");

        assertThat(httpRequest.getPath()).isEqualTo("./static/css/styles.css");
    }

    @DisplayName("요청에 해당하는 동적 처리 경로를 반환한다.")
    @Test
    void getPath_whenRequestNotResources() {
        RequestLine requestLine = RequestLine.of(
            "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");
        String header = "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*";
        HttpRequest httpRequest = new HttpRequest(requestLine, header, "");

        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
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

    @DisplayName("POST 요청에 대한 HttpRequest 객체를 생성한다. ")
    @Test
    void of_whenRequestMethodIsPost() throws IOException {
        File file = new File("/Users/moon/Desktop/Github/jwp-was/build/resources/test/PostRequest.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        HttpRequest httpRequest = HttpRequest.of(br);

        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
    }
}