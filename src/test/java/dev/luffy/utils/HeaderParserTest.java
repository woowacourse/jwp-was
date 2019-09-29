package dev.luffy.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeaderParserTest {

    @DisplayName("헤더 내용이 들어있는 String List 를 Map 으로 파싱한다.")
    @Test
    void parsingHeaderString() {
        List<String> lines = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        );
        Map<String, String> headers = HeaderParser.parse(lines);
        assertEquals(headers.get("Host"), "localhost:8080");
        assertEquals(headers.get("Connection"), "keep-alive");
        assertEquals(headers.get("Accept"), "*/*");
    }
}
