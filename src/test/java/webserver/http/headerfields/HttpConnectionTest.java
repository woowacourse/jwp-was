package webserver.http.headerfields;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpConnectionTest {
    @Test
    @DisplayName("String 타입으로 HttpConnection CLOSE 타입을 찾는다.")
    void findHttpConnection() {
        String inputData = "close";

        HttpConnection close = HttpConnection.of(inputData).get();

        assertThat(close).isEqualTo(HttpConnection.CLOSE);
        assertThat(close.toString()).isEqualTo(inputData);
        assertThat(close.nameOfField()).isEqualTo("Connection");
    }

    @Test
    @DisplayName("String 타입으로 HttpConnection KEEP_ALIVE 타입을 찾는다.")
    void findHttpConnection2() {
        String inputData = "keep-alive";

        HttpConnection close = HttpConnection.of(inputData).get();

        assertThat(close).isEqualTo(HttpConnection.KEEP_ALIVE);
        assertThat(close.toString()).isEqualTo(inputData);
        assertThat(close.nameOfField()).isEqualTo("Connection");
    }

    @Test
    @DisplayName("String 타입으로 HttpConnection UPGRADE 타입을 찾는다.")
    void findHttpConnection3() {
        String inputData = "upgrade";

        HttpConnection close = HttpConnection.of(inputData).get();

        assertThat(close).isEqualTo(HttpConnection.UPGRADE);
        assertThat(close.toString()).isEqualTo(inputData);
        assertThat(close.nameOfField()).isEqualTo("Connection");
    }

    @Test
    @DisplayName("존재하지 않는 HttpConnection 타입을 찾는 경우 예외가 발생한다.")
    void findHttpConnectionFail() {
        String inputData = "testType";

        assertThrows(IllegalArgumentException.class, () ->
                HttpConnection.of(inputData).orElseThrow(IllegalArgumentException::new)
        );
    }
}