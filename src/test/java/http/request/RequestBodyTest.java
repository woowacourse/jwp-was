package http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RequestBodyTest {

    @Test
    void emptyBody() {
        RequestBody requestBody = RequestBody.emptyBody();

        assertThat(requestBody.getBody().isEmpty()).isTrue();
    }

    @Test
    void getValue() {
        RequestBody requestBody = new RequestBody("userId=javajigi&password=password&name=자바지기&email=javajigi@slipp.net");

        assertThat(requestBody.getValue("userId")).isEqualTo("javajigi");
        assertThat(requestBody.getValue("password")).isEqualTo("password");
        assertThat(requestBody.getValue("name")).isEqualTo("자바지기");
        assertThat(requestBody.getValue("email")).isEqualTo("javajigi@slipp.net");
    }
}