package implementedfilter;

import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import http.session.HttpSession;
import http.session.HttpSessionStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.filter.Filter;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AuthFilterTest {
    private static final String LOGINED_REQUEST_STRING =
        "GET /user/list HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cookie: jsessionid=%s\n"
            + "\n";

    private static final String NOT_LOGINED_REQUEST_STRING =
        "GET /user/list HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "\n";

    private Filter filter;

    @BeforeEach
    void setUp() {
        filter = new AuthFilter();
    }

    @Test
    void returnFalseForNotLogined() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(NOT_LOGINED_REQUEST_STRING));

        RequestEntity requestEntity = RequestEntity.from(bufferedReader);
        ResponseEntity responseEntity = ResponseEntity.empty();

        boolean result = filter.doFilter(requestEntity, responseEntity);

        assertAll(
            () -> assertThat(result).isFalse(),
            () -> assertThat(responseEntity.getHttpHeader().findOrEmpty("Location")).isEqualTo("/user/login.html"),
            () -> assertThat(responseEntity.getHttpStatus()).isEqualTo(HttpStatus.FOUND)
        );
    }

    @Test
    void returnTrueForLogined() {
        HttpSession httpSession = HttpSessionStorage.generate("yuan");
        BufferedReader bufferedReader = new BufferedReader(
            new StringReader(String.format(LOGINED_REQUEST_STRING, httpSession.getId().toString()))
        );

        RequestEntity requestEntity = RequestEntity.from(bufferedReader);
        ResponseEntity responseEntity = ResponseEntity.empty();

        boolean result = filter.doFilter(requestEntity, responseEntity);

        assertThat(result).isTrue();
    }
}