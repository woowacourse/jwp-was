package webserver.http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import webserver.http.service.HttpGetService;
import webserver.http.service.HttpPostService;

public class HttpRequestTest {
    @TestFactory
    Stream<DynamicTest> getService() {
        return Stream.of(
                dynamicTest("GET 요청시 HttpGetService를 반환한다.", this::serviceOfGet),
                dynamicTest("POST 요청시 HttpPostService를 반환한다.", this::serviceOfPost)
        );
    }

    private void serviceOfGet() throws IOException {
        String request = "GET test HTTP/1.1";
        StringReader sr = new StringReader(request);
        BufferedReader br = new BufferedReader(sr);
        HttpRequest httpRequest = new HttpRequestFactory().create(br);

        assertThat(httpRequest.createService()).isInstanceOf(HttpGetService.class);
    }

    private void serviceOfPost() throws IOException {
        String request = "POST test HTTP/1.1";
        StringReader sr = new StringReader(request);
        BufferedReader br = new BufferedReader(sr);
        HttpRequest httpRequest = new HttpRequestFactory().create(br);

        assertThat(httpRequest.createService()).isInstanceOf(HttpPostService.class);
    }
}
