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
}