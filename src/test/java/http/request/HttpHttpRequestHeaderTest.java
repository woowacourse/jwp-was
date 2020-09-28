package http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HttpHttpRequestHeaderTest {

    @Test
    void add() {
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
        httpRequestHeader.add("HOST", "localhost:8080");
        httpRequestHeader.add("Connection", "keep-alive");

        assertThat(httpRequestHeader.getValue("HOST")).isEqualTo("localhost:8080");
        assertThat(httpRequestHeader.getValue("Connection")).isEqualTo("keep-alive");
    }
}