package web;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static web.HttpRequestFixture.*;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {
    @DisplayName("HttpRequest를 생성한다.")
    @Test
    public void HttpRequest() throws IOException {
        assertThat(createRequest(GET, INDEX_HTML))
                .isInstanceOf(HttpRequest.class);
    }

    @DisplayName("요청이 POST인지 확인한다.")
    @Test
    void isPost() throws IOException {
        assertTrue(new HttpRequest(createBufferedReader(REQUEST)).isPost());
    }

    @DisplayName("요청의 Uri를 추출한다.")
    @Test
    void getRequestUri() throws IOException {
        assertEquals(INDEX_HTML, createRequest(GET, INDEX_HTML).getRequestUri().getUri());
    }
}
