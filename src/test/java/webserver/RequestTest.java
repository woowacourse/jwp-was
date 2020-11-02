package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class RequestTest {

    @Test
    void validRequest() throws IOException {
        String input = "GET / HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\n" + "";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Request request = new Request(inputStream);
        assertThat(request).hasToString(input);
    }

    @Test
    public void nullRequest() throws IOException {
        Request request = new Request(null);
        assertThat(request).hasToString("");
    }
}
