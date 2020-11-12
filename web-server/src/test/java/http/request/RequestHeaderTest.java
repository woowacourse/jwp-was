package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHeaderTest {

    @DisplayName("Request Headers 조회")
    @Test
    void getHeaders() throws IOException {
        String request = "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n";

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(request.getBytes())));

        RequestHeader requestHeader = new RequestHeader(bufferedReader);
        Map<String, String> headers = requestHeader.getHeaders();

        assertAll(
            () -> assertThat(headers).containsEntry("Host", "localhost:8080"),
            () -> assertThat(headers).containsEntry("Connection", "keep-alive"),
            () -> assertThat(headers).containsEntry("Cache-Control", "max-age=0")
        );
    }
}
