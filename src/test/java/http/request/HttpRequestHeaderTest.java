package http.request;

import http.request.exception.NotFoundHttpRequestHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestHeaderTest {

    @Test
    void of_올바른경우() {
        List<String> headerLines = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        );
        HttpRequestHeader httpRequestHeader = HttpRequestHeader.of(headerLines);

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Host", "localhost:8080");
        expectedHeaders.put("Connection", "keep-alive");
        expectedHeaders.put("Accept", "*/*");

        for (String expectedKey : expectedHeaders.keySet()) {
            assertThat(expectedHeaders.get(expectedKey))
                    .isEqualTo(httpRequestHeader.getHeader(expectedKey));
        }
    }

    @Test
    void getHeader_키가_존재하지_않는_경우() {
        List<String> headerLines = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        );
        HttpRequestHeader httpRequestHeader = HttpRequestHeader.of(headerLines);

        String notExistKey = "notExist";
        assertThrows(NotFoundHttpRequestHeader.class, () -> httpRequestHeader.getHeader(notExistKey));
    }

    @Test
    @DisplayName("대소문자 구분없이 키 적용")
    void _() {
        String insertedKey = "Content-Length";
        HttpRequestHeader header = HttpRequestHeader.of(Arrays.asList(
                "Content-Length: 10"
        ));
        List<String> keys = Arrays.asList(
                "Content-Length",
                "Content-length",
                "content-Length",
                "content-length"
        );

        for (String key : keys) {
            assertThat(header.contains(key)).isTrue();
            assertThat(header.getHeader(key)).isEqualTo(header.getHeader(insertedKey));
        }
    }
}









