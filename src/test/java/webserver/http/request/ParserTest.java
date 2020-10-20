package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {
    @DisplayName("Parameter 파싱 테스트")
    @Test
    void parseParameterTEst() throws IOException {
        Map<String, String> actual = Parser.parseParameter("userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net");

        assertThat(actual).hasSize(4);
        assertThat(actual.get("userId")).isEqualTo("javajigi");
        assertThat(actual.get("password")).isEqualTo("password");
        assertThat(actual.get("name")).isEqualTo("박재성");
        assertThat(actual.get("email")).isEqualTo("javajigi@slipp.net");
    }

    @DisplayName("Header 파싱 테스트")
    @Test
    void paresHeaderTest() throws UnsupportedEncodingException {
        Parser.Pair pair = Parser.parseHeader("Connection: keep-alive");

        assertThat(pair.getKey()).isEqualTo("Connection");
        assertThat(pair.getValue()).isEqualTo("keep-alive");
    }

    @DisplayName("Header 파싱 예외 테스트")
    @Test
    void paresHeaderExceptionTest() throws UnsupportedEncodingException {
        Parser.Pair pair = Parser.parseHeader("Host: localhost:8080\r\n");

        assertThat(pair.getKey()).isEqualTo("Host");
        assertThat(pair.getValue()).isEqualTo("localhost:8080");
    }

}
