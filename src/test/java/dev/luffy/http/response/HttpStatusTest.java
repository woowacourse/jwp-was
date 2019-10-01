package dev.luffy.http.response;

import dev.luffy.http.response.exception.NotSupportedHttpResponseStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpStatusTest {

    @DisplayName("코드 200으로 Http Status OK 를 정상적으로 생성한다.")
    @Test
    void constructOkSuccessfully() {
        assertEquals(HttpStatus.of(200), HttpStatus.OK);
    }

    @DisplayName("Http Status OK 에 대해 getResponseLine 메서드가 잘 동작한다.")
    @Test
    void getResponseLineOk() {
        assertEquals(HttpStatus.OK.getResponseLine(), "200 OK");
    }

    @DisplayName("코드 302로 Http Status Found 를 정상적으로 생성한다.")
    @Test
    void constructFoundSuccessfully() {
        assertEquals(HttpStatus.of(302), HttpStatus.FOUND);

    }

    @DisplayName("Http Status FOUND 에 대해 getResponseLine 메서드가 잘 동작한다.")
    @Test
    void getResponseLineFound() {
        assertEquals(HttpStatus.FOUND.getResponseLine(), "302 FOUND");
    }

    @DisplayName("코드 404로 Http Status Not Found 를 정상적으로 생성한다.")
    @Test
    void constructNotFoundSuccessfully() {
        assertEquals(HttpStatus.of(404), HttpStatus.NOT_FOUND);

    }

    @DisplayName("Http Status NOT_FOUND 에 대해 getResponseLine 메서드가 잘 동작한다.")
    @Test
    void getResponseLineNotFound() {
        assertEquals(HttpStatus.NOT_FOUND.getResponseLine(), "404 NOT FOUND");
    }

    @DisplayName("지원하지 않는 Http Status Code 는 에러를 던진다.")
    @Test
    void constructFailed() {
        assertThrows(NotSupportedHttpResponseStatus.class, () -> HttpStatus.of(1));
    }
}