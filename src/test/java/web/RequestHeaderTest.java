package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {

    @DisplayName("RequestHeader headers 확인")
    @Test
    void requestHeaderTest2() {
        List<String> lines = new ArrayList<>();
        lines.add("Host: localhost:8080");
        lines.add("Connection: keep-alive");

        RequestHeader requestHeader = new RequestHeader(lines);

        Map<String, String> headers = requestHeader.getHeaders();

        assertThat(headers.get("Host")).isEqualTo("localhost:8080");
        assertThat(headers.get("Connection")).isEqualTo("keep-alive");
    }
}