package http;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class HttpHeadersTest {
    String testDirectory = "./src/test/resources/";

    @Test
    void fromTest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "headers.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpHeaders httpHeaders = HttpHeaders.from(br);
        assertAll(() -> {
            assertEquals("localhost:8080", httpHeaders.get(HeaderType.HOST));
            assertEquals("keep-alive", httpHeaders.get(HeaderType.CONNECTION));
            assertEquals("*/*", httpHeaders.get(HeaderType.ACCEPT));
        });
    }

}
