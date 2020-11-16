package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.HttpRequestFixture;

class RequestLineTest {
    @DisplayName("RequestLine 객체를 생성한다.")
    @Test
    void of_shouldReturnRequestLine() throws IOException {
        BufferedReader bufferedReader = HttpRequestFixture.bufferedReaderOfRequestLine();

        RequestLine requestLine = RequestLine.of(bufferedReader);

        assertThat(requestLine).isNotNull();
    }

    @DisplayName("요청라인이 비어있는 경우 예외를 던진다.")
    @Test
    void of_fromEmptyRequest_shouldThrowException() throws IOException {
        BufferedReader bufferedReader = HttpRequestFixture.bufferedReaderOfEmptyRequestLine();

        assertThatThrownBy(() -> RequestLine.of(bufferedReader))
            .isInstanceOf(NotReadableHttpMessageException.class)
            .hasMessage("HTTP 요청메시지의 요청라인이 비어있습니다.");
    }
}