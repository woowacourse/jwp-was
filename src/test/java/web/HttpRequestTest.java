package web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

    @Test
    void name() throws IOException {
        InputStream inputStream = new FileInputStream("./src/test/resources/UserCreateRequest.txt");

        HttpRequest httpRequest = new HttpRequest(
            new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream))));

        assertThat(httpRequest.getRequestBody())
            .isEqualTo("userId=javajigi&password=password&name=pobi&email=javajigi@slipp.net");
    }
}