package http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpRequestUrlTest {

    private HttpRequestUrl httpRequestUrl;

    @BeforeEach
    void serUp() {
        httpRequestUrl = new HttpRequestUrl("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }

    @Test
    void getUrl() {
        assertThat(httpRequestUrl.getUrl()).isEqualTo("/user/create");
    }

    @Test
    void getValue() {
        assertThat(httpRequestUrl.getValue("userId")).isEqualTo("javajigi");
        assertThat(httpRequestUrl.getValue("password")).isEqualTo("password");
        assertThat(httpRequestUrl.getValue("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(httpRequestUrl.getValue("email")).isEqualTo("javajigi%40slipp.net");
    }
}