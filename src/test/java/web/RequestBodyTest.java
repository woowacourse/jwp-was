package web;

import static org.junit.jupiter.api.Assertions.*;
import static web.HttpRequestFixture.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestBodyTest {
    @DisplayName("body의 데이터를 Map으로 추출한다.")
    @Test
    void getFormData() throws IOException {
        Map<String, String> expected = new HashMap<String, String>() {{
            put("userId", "javajigi");
            put("password", "password");
            put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
            put("email", "javajigi%40slipp.net");
        }};
        Map<String, String> actual = new RequestBody(createBufferedReader(BODY)).getFormData();
        assertEquals(expected, actual);
    }
}