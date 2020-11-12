package http.request;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import exceptions.InvalidHttpRequestException;
import http.HttpMethod;
import http.HttpVersion;

class RequestLineTest {

    @DisplayName("RequestLine 파싱")
    @Test
    void parseRequestLine() {
        String request = "GET /favicon.ico HTTP/1.1";
        String[] req = request.split(" ");
        String path = req[1];
        String version = req[2];

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(request.getBytes())
            ));

        RequestLine requestLine = new RequestLine(bufferedReader);

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo(path);
        assertThat(requestLine.getVersion()).isEqualTo(HttpVersion.from(version));
    }

    @DisplayName("유효하지 않은 RequestLine - 예외 발생")
    @ParameterizedTest
    @EmptySource
    void invalid_RequestLine_Should_Throw_Exception(String request) {
        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(request.getBytes())
            ));

        assertThatThrownBy(() -> new RequestLine(bufferedReader))
            .isInstanceOf(InvalidHttpRequestException.class)
            .hasMessage("유효한 HTTP 요청이 아닙니다.");
    }

    @DisplayName("유효하지 않은 RequestLine - 예외 발생2")
    @ParameterizedTest
    @ValueSource(strings = {"GET /favicon.ico HTTP", "GET /favicon.ico HTTP1.1", "GET/favicon.icoHTTP/1.1",
        "/favicon.ico"})
    void invalid_RequestLine_Should_Throw_Exception2(String request) {
        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(request.getBytes())
            ));

        assertThatThrownBy(() -> new RequestLine(bufferedReader))
            .isInstanceOf(InvalidHttpRequestException.class)
            .hasMessage("유효한 HTTP 요청이 아닙니다.");
    }
}
