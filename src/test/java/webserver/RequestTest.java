package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RequestTest {

    private String input;
    private InputStream inputStream;

    @BeforeEach
    void setUp() {
        input = "GET /index.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
    }

    @Test
    void parse() throws IOException {
        Request request = new Request(inputStream);
        assertThat(request).hasToString(input);
    }

    @Test
    public void parseNull() throws IOException {
        Request request = new Request(null);
        assertThat(request).hasToString("");
    }

    @Test
    void getPath() throws IOException {
        Request request = new Request(inputStream);
        assertThat(request.getPath()).isEqualTo("/index.html");
    }
}
