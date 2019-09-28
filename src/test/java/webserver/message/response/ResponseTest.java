package webserver.message.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.message.HttpStatus;
import webserver.message.HttpVersion;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseTest {
    private Response response;

    @BeforeEach
    void setUp() {
        String body = "<h1>HI</h1>";
        this.response = new Response
                .Builder(HttpVersion.HTTP_1_1, HttpStatus.OK)
                .body(body)
                .build();
    }

    @Test
    @DisplayName("응답 메시지를 정확하게 생성하는지 확인")
    void toBytes() {
        byte[] expect = ("HTTP/1.1 200 OK\r\n" +
                "Content-Length: 11\r\n" +
                "Content-Type: text/html; charset=utf-8\r\n" +
                "\r\n" +
                "<h1>HI</h1>").getBytes();

        assertThat(response.toBytes()).isEqualTo(expect);
    }

}