package web;

import org.junit.jupiter.api.Test;
import web.request.HttpRequest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @Test
    void checkRequest() throws IOException {
        InputStream inputStream = new FileInputStream("./src/test/resources/UserCreateRequest.txt");

        HttpRequest httpRequest = new HttpRequest(
                new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream))));

        assertThat(httpRequest.isGetMethod()).isFalse();
        assertThat(httpRequest.getRequestBody().getBody())
                .isEqualTo("userId=javajigi&password=password&name=pobi&email=javajigi@slipp.net");
        assertThat(httpRequest.getRequestPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getContentLength()).isEqualTo(68);
    }
}