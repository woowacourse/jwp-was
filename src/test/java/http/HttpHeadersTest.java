package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpHeadersTest {

    @DisplayName("올바른 헤더 정보로 HttpHeader 객체를 생성한다.")
    @Test
    void createHttpHeader() throws IOException {
        String headerInput = "Content-Type: text/html;charset=utf-8\r\n"
            + "Content-Length: 128\r\n"
            + "Accept-Language: en-US,en;q=0.9\r\n"
            + "Cookie: JSESSIONID=D03222408A13F2797D6DFECB7CFC74EE\r\n";

        Reader inputString = new StringReader(headerInput);
        BufferedReader reader = new BufferedReader(inputString);

        HttpHeaders httpHeaders = new HttpHeaders(reader);

        assertAll(
            () -> assertThat("text/html;charset=utf-8").isEqualTo(httpHeaders.getValue("Content-Type")),
            () -> assertThat("128").isEqualTo(httpHeaders.getValue("Content-Length")),
            () -> assertThat("en-US,en;q=0.9").isEqualTo(httpHeaders.getValue("Accept-Language")),
            () -> assertThat("JSESSIONID=D03222408A13F2797D6DFECB7CFC74EE").isEqualTo(httpHeaders.getValue("Cookie"))
        );
    }
}