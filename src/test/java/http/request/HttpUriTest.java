package http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpUriTest {

    @Test
    void path() {
        HttpUri httpUri = HttpUri.of("/index.html?userId=test&userPassword=1234");
        assertThat(httpUri.getPath()).isEqualTo("/index.html");
    }

    @Test
    void queryParam() {
        HttpUri httpUri = HttpUri.of("/index.html?userId=test&userPassword=1234");
        assertThat(httpUri.getQueryParamValue("userId")).isEqualTo("test");
    }

    @Test
    void noParam() {
        HttpUri httpUri = HttpUri.of("/index.html");
        assertThat(httpUri.getQuery()).isEqualTo(QueryParameter.empty());
    }
}