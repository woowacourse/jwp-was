package utils;

import static org.junit.jupiter.api.Assertions.*;
import static web.HttpRequestFixture.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.HttpRequestFixture;

class HttpRequestParserTest {
    @DisplayName("요청에서 header를 추출한다.")
    @Test
    void parsingRequestHeader() throws IOException {
        Map<String, String> expected = new HashMap<String, String>() {{
            put("requestLine", "POST /user/create HTTP/1.1");
            put("Host", "localhost:8080");
            put("Connection", "keep-alive");
            put("Content-Length", String.valueOf(JAVAJIGI_DATA.length()));
            put("Accept", "*/*");
        }};
        BufferedReader br = HttpRequestFixture.createBufferedReader(
                HttpRequestFixture.REQUEST);

        Map<String, String> actual = HttpRequestParser.parsingRequestHeader(br);

        assertEquals(expected, actual);
    }

    @DisplayName("parameter와 body의 데이터를 Map으로 추출한다.")
    @Test
    void parsingData() {
        Map<String, String> expected = new HashMap<String, String>() {{
            put("userId", "javajigi");
            put("password", "password");
            put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
            put("email", "javajigi%40slipp.net");
        }};
        Map<String, String> actual = HttpRequestParser.parsingData(JAVAJIGI_DATA);
        assertEquals(expected, actual);
    }
}
