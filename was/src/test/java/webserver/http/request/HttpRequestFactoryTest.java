package webserver.http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static utils.Strings.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.HttpVersion;

class HttpRequestFactoryTest {
    private HttpRequestFactory httpRequestFactory;

    @BeforeEach
    void setUp() {
        httpRequestFactory = new HttpRequestFactory();
    }

    @DisplayName("HttpRequest GET 요청 생성 성공")
    @Test
    void create() throws IOException {
        String method = "GET";
        String uri = "/index.html";
        String version = "HTTP/1.1";

        StringReader sr = new StringReader(method + SP + uri + SP + version);
        BufferedReader br = new BufferedReader(sr);
        HttpRequest request = httpRequestFactory.create(br);

        assertAll(
                () -> assertThat(request.getRequestLine().getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(request.getRequestLine().getRequestURI().getUri()).isEqualTo(uri),
                () -> assertThat(request.getRequestLine().getVersion()).isEqualTo(
                        HttpVersion.HTTP_1_1)
        );
    }
}