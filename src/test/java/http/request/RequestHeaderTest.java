package http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RequestHeaderTest {

    @Test
    void add() {
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.add("HOST", "localhost:8080");
        requestHeader.add("Connection", "keep-alive");

        assertThat(requestHeader.getValue("HOST")).isEqualTo("localhost:8080");
        assertThat(requestHeader.getValue("Connection")).isEqualTo("keep-alive");
    }
}