package http.request;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestCreatorTest {

    @Test
    void create() throws IOException {
        String httpRequestMessage = "POST /user/create?address=ny HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=gg&email=javajigislipp.net";

        HttpRequest httpRequest = HttpRequestCreator.create(createInputStream(httpRequestMessage));
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getMethod()).isEqualTo(RequestMethod.POST);
        assertThat(httpRequest.getFormDataParameter("userId")).isEqualTo("javajigi");
        assertThat(httpRequest.getFormDataParameter("password")).isEqualTo("password");
        assertThat(httpRequest.getQueryParameter("address")).isEqualTo("ny");
    }

    InputStream createInputStream(String str) {
        return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    }
}