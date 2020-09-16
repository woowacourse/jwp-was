package webserver.filter;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static webserver.RequestHandlerIntegrationTest.*;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import http.ContentType;
import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;

class StaticFileFilterTest {

    private static final String NON_STATIC_REQUEST_STRING =
        "POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 93\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";


    private static final String STATIC_REQUEST_STRING =
        "GET /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*\n";

    private Filter filter;

    @BeforeEach
    void setUp() {
        filter = new StaticFileFilter();
    }

    @Test
    void returnFalseForStaticFile() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(STATIC_REQUEST_STRING));

        RequestEntity requestEntity = RequestEntity.from(bufferedReader);
        ResponseEntity responseEntity = ResponseEntity.empty();

        boolean result = filter.doFilter(requestEntity, responseEntity);

        ResponseEntity expected = ResponseEntity.generateWithStatus(HttpStatus.OK)
            .version("HTTP/1.1")
            .body(RESPONSE_BODY, ContentType.HTML);

        assertAll(
            () -> assertThat(result).isFalse(),
            () -> assertThat(responseEntity).usingRecursiveComparison().isEqualTo(expected)
        );
    }

    @Test
    void returnTrueForNonStaticFile() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(NON_STATIC_REQUEST_STRING));

        RequestEntity requestEntity = RequestEntity.from(bufferedReader);
        ResponseEntity responseEntity = ResponseEntity.empty();

        boolean result = filter.doFilter(requestEntity, responseEntity);

        ResponseEntity expected = ResponseEntity.empty();

        assertAll(
            () -> assertThat(result).isTrue(),
            () -> assertThat(responseEntity).usingRecursiveComparison().isEqualTo(expected)
        );
    }
}