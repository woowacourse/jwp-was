package http;

import http.request.exception.NotFoundHttpRequestHeader;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpHeaderTest {

    @Test
    void request_헤더_생성() {
        List<String> headerLines = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        );
        HttpHeader httpHeader = HttpHeader.create(headerLines);

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Host", "localhost:8080");
        expectedHeaders.put("Connection", "keep-alive");
        expectedHeaders.put("Accept", "*/*");

        for (String expectedKey : expectedHeaders.keySet()) {
            assertThat(expectedHeaders.get(expectedKey))
                    .isEqualTo(httpHeader.getHeader(expectedKey));
        }
    }

    @Test
    void response_헤더_생성() {
        List<String> headerLines =
                Arrays.asList("Content-Type: text/html;charset=utf-8\r\n",
                        "Content-Length: 10\r\n");
        HttpHeader httpHeader = HttpHeader.create(headerLines);

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Content-Type", "text/html;charset=utf-8\r\n");
        expectedHeaders.put("Content-Length", "10\r\n");

        for (String expectedKey : expectedHeaders.keySet()) {
            assertThat(expectedHeaders.get(expectedKey))
                    .isEqualTo(httpHeader.getHeader(expectedKey));
        }
    }

    @Test
    void getHeader_키가_존재하지_않는_경우() {
        List<String> headerLines = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        );
        HttpHeader httpHeader = HttpHeader.create(headerLines);

        String notExistKey = "notExist";
        assertThrows(NotFoundHttpRequestHeader.class, () -> httpHeader.getHeader(notExistKey));

    }
}