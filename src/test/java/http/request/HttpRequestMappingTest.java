package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.AbstractHttpRequestGenerator;

class HttpRequestMappingTest extends AbstractHttpRequestGenerator {

    @DisplayName("GET 매핑 일치 여부 체크")
    @Test
    void match_GET() throws IOException {
        HttpRequest expected = createHttpGetRequest("GET_IndexHTML");

        assertAll(
            () -> assertThat(HttpRequestMapping.GET("/index.html").match(expected)).isTrue(),
            () -> assertThat(HttpRequestMapping.POST("/index.html").match(expected)).isFalse()
        );

    }

    @DisplayName("POST 매핑 일치 여부 체크")
    @Test
    void match_POST() throws IOException {
        HttpRequest expected = createHttpPostRequest("POST_UserCreateRequest");

        assertAll(
            () -> assertThat(HttpRequestMapping.POST("/user/create").match(expected)).isTrue(),
            () -> assertThat(HttpRequestMapping.GET("/user/create").match(expected)).isFalse()
        );
    }

}
