package http.request;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class HttpHttpRequestHeaderTest {

    @Test
    void add() {
        final Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("HOST", "localhost:8080");
        requestHeader.put("Connection", "keep-alive");

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(requestHeader);

        assertThat(httpRequestHeader.getValue("HOST")).isEqualTo("localhost:8080");
        assertThat(httpRequestHeader.getValue("Connection")).isEqualTo("keep-alive");
    }
}