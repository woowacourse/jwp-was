package http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HttpRequestBodyTest {

    @Test
    void emptyBody() {
        HttpRequestBody httpRequestBody = HttpRequestBody.emptyBody();

        assertThat(httpRequestBody.getBody().isEmpty()).isTrue();
    }

    @Test
    void getValue() {
        HttpRequestBody httpRequestBody = new HttpRequestBody("userId=javajigi&password=password&name=자바지기&email=javajigi@slipp.net");

        assertThat(httpRequestBody.getValue("userId")).isEqualTo("javajigi");
        assertThat(httpRequestBody.getValue("password")).isEqualTo("password");
        assertThat(httpRequestBody.getValue("name")).isEqualTo("자바지기");
        assertThat(httpRequestBody.getValue("email")).isEqualTo("javajigi@slipp.net");
    }
}