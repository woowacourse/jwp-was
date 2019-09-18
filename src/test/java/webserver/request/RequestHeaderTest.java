package webserver.request;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void create() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(testDirectory + "request_header_test.txt"));

        RequestHeader requestHeader = new RequestHeader(bufferedReader);
        assertThat(requestHeader.getHeader("Content-Length")).isEqualTo("59");
    }
}