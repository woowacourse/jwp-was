package was.webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpHeaderTest {
    @DisplayName("입력한 헤더에 맞게 파싱하는지 테스트")
    @Test
    void ofTest() {
        String input = "Host: localhost:8080";
        HttpHeader httpHeader = HttpHeader.of(input);

        assertAll(
                () -> assertThat(httpHeader.getType()).isEqualTo("Host"),
                () -> assertThat(httpHeader.getContent()).isEqualTo("localhost:8080")
        );
    }

    @DisplayName("헤더의 타입과 내용으로 생성자 테스트")
    @Test
    void ofTestWithTypeAndContent() {
        String type = "Set-Cookie";
        String content = "logined=false";
        HttpHeader httpHeader = HttpHeader.of(type, content);

        assertAll(
                () -> assertThat(httpHeader.getType()).isEqualTo("Set-Cookie"),
                () -> assertThat(httpHeader.getContent()).isEqualTo("logined=false")
        );
    }

    @DisplayName("헤더 정보 출력")
    @Test
    void getHttpHeaderStringTest() {
        String input = "Host: localhost:8080";
        HttpHeader httpHeader = HttpHeader.of(input);
        String httpHeaderString = httpHeader.getHttpHeaderString();

        assertThat(httpHeaderString).isEqualTo(input);
    }
}
