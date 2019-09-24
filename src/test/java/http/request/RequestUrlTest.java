package http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestUrlTest {
    private RequestUrl requestUrl;

    @BeforeEach
    public void setUp() {
        requestUrl = new RequestUrl("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }

    @Test
    public void getUrl() {
        requestUrl = new RequestUrl("/");
        assertThat(requestUrl.getUrl()).isEqualTo("/index.html");
    }

    @Test
    public void getQueryParameter() {
        assertThat(requestUrl.getQueryParameter()).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }

    @Test
    public void hasQueryParameter() {
        assertThat(requestUrl.hasQueryParameter()).isTrue();
    }
}
