package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpUrlTest {

    @Test
    @DisplayName("입력된 문자열에서 url과 파라미터를 분리해 HttpUrl 생성")
    void createHttpUrlTest() {
        String url = "/user/create?userId=javajigi&password=password&name=myname&email=email@email.com";
        HttpUrl requestUrl = HttpUrl.from(url);
        assertAll(() -> {
            assertThat(requestUrl.getUrl()).isEqualTo("/user/create");

            assertThat(requestUrl.getParam("userId")).isEqualTo("javajigi");
            assertThat(requestUrl.getParam("password")).isEqualTo("password");
            assertThat(requestUrl.getParam("name")).isEqualTo("myname");
            assertThat(requestUrl.getParam("email")).isEqualTo("email@email.com");
        });
    }
}
