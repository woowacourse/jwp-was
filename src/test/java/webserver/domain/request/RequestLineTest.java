package webserver.domain.request;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestLineTest {
    @DisplayName("RequestLine 객체를 생성한다.")
    @Test
    void of_shouldReturnRequestLine() throws IOException {
        InputStream inputStream = new FileInputStream(
            new File("/Users/moon/Desktop/Github/jwp-was/build/resources/test/RequestLine.txt"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        RequestLine requestLine = RequestLine.of(bufferedReader);

        assertThat(requestLine).isNotNull();
    }

    @DisplayName("요청라인이 비어있는 경우 예외를 던진다.")
    @Test
    void of_fromEmptyRequest_shouldThrowException() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(
            new File("/Users/moon/Desktop/Github/jwp-was/build/resources/test/EmptyRequestLine.txt"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        assertThatThrownBy(() -> RequestLine.of(bufferedReader))
            .isInstanceOf(NotReadableHttpMessageException.class)
            .hasMessage("HTTP 요청메시지의 요청라인이 비어있습니다.");
    }
}