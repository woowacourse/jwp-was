package utils;

import static org.junit.jupiter.api.Assertions.*;
import static web.HttpRequestFixture.*;
import static web.RequestHeader.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.HttpRequestFixture;

class HttpRequestParserTest {
    @DisplayName("요청에서 header를 추출한다.")
    @Test
    void parsingRequestHeader() throws IOException {
        List<String> expected = Arrays.stream(HttpRequestFixture.header.split(NEW_LINE))
                .filter(value -> value != null && !value.isEmpty())
                .collect(Collectors.toList());

        BufferedReader br = HttpRequestFixture.createBufferedReader(
                HttpRequestFixture.request);
        List<String> actual = HttpRequestParser.parsingRequestHeader(br);

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