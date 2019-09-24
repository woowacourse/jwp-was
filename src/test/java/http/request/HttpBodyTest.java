package http.request;

import org.junit.jupiter.api.Test;
import webserver.ServerErrorException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpBodyTest {

    @Test
    void of_디코딩_실패() {
        assertThrows(ServerErrorException.class, () -> HttpBody.of("userId=javajigi&password=password&name=%EB%B0%"));
    }
}